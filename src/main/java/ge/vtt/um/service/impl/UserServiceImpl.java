package ge.vtt.um.service.impl;

import ge.vtt.um.component.utils.JwtUtils;
import ge.vtt.um.entity.PasswordResetEntity;
import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.model.request.GeneralRequest;
import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.repository.PasswordResetRepository;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.service.UserService;
import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import ge.vtt.um.service.exception.UserPasswordIsNotMatchedException;
import ge.vtt.um.service.exception.VerificationCodeIsNotMatchedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.UUID;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordResetRepository passwordResetRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    private final JavaMailSenderImpl javaMailSender;

    private final Environment environment;

    @Override
    public void performRegistration(GeneralRequest request) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setFirstname(request.getFirstname());
        userEntity.setLastname(request.getLastname());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);
        userRepository.flush();
    }

    @Override
    public Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new UserPasswordIsNotMatchedException("Provided password for user is not matched!");
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtUtils.generateJWT(authentication);
    }

    @Override
    public void startPasswordResetProcess(ResetPasswordPromptRequest request) throws UserNotFoundException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        PasswordResetEntity passwordResetEntity = new PasswordResetEntity();
        passwordResetEntity.setUser(userEntity);
        passwordResetEntity.setCreationDate(LocalDateTime.now());

        String verificationCode = UUID.randomUUID().toString().substring(0, 5);
        passwordResetEntity.setEncryptedCode(passwordEncoder.encode(verificationCode));

        passwordResetRepository.save(passwordResetEntity);
        passwordResetRepository.flush();

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(javaMailSender.getUsername());
        mailMessage.setTo(userEntity.getEmail());
        mailMessage.setText(String.format(environment.getProperty("template.password.reset.prompt"), verificationCode));
        mailMessage.setSubject("Reset password");
        javaMailSender.send(mailMessage);
    }

    @Override
    public void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }

        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        PasswordResetEntity passwordResetEntity = passwordResetRepository.getAllByUser(userEntity).stream().max(Comparator.comparing(PasswordResetEntity::getCreationDate)).get();
        if (!passwordEncoder.matches(request.getVerificationCode(), passwordResetEntity.getEncryptedCode())) {
            throw new VerificationCodeIsNotMatchedException("Provided verification code is not matched!");
        }

        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userEntity);
        userRepository.flush();
        passwordResetRepository.delete(passwordResetEntity);
        passwordResetRepository.flush();
    }

}
