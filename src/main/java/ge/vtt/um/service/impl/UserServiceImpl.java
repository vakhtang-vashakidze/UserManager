package ge.vtt.um.service.impl;

import ge.vtt.um.component.utils.JwtUtils;
import ge.vtt.um.entity.PasswordResetEntity;
import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.entity.UserVerificationEntity;
import ge.vtt.um.model.request.GeneralRequest;
import ge.vtt.um.model.request.RegisterVerifyRequest;
import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.repository.PasswordResetRepository;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.repository.UserVerificationRepository;
import ge.vtt.um.service.UMMailSender;
import ge.vtt.um.service.UserService;
import ge.vtt.um.service.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static ge.vtt.um.service.exception.ExceptionMessages.*;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordResetRepository passwordResetRepository;

    private final UserVerificationRepository userVerificationRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final UMMailSender mailSender;

    @Override
    public void performRegistration(GeneralRequest request) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(USER_ALREADY_EXISTS.getMessage());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(request.getUsername());
        userEntity.setFirstname(request.getFirstname());
        userEntity.setLastname(request.getLastname());
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setVerified(false);

        UserVerificationEntity userVerificationEntity = new UserVerificationEntity();
        userVerificationEntity.setUser(userEntity);
        userVerificationEntity.setCreationDate(LocalDateTime.now());

        String verificationCode = UUID.randomUUID().toString().substring(0, 5);
        userVerificationEntity.setEncryptedCode(passwordEncoder.encode(verificationCode));

        userRepository.save(userEntity);
        userRepository.flush();
        userVerificationRepository.save(userVerificationEntity);
        userVerificationRepository.flush();

        mailSender.sendRegisterVerificationMail(userEntity.getEmail(), verificationCode);
    }

    @Override
    public void performRegisterVerification(RegisterVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, UserVerificationEntityNotFoundException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException(USER_NOT_FOUND.getMessage());
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        Optional<UserVerificationEntity> userVerificationEntity = userVerificationRepository.getAllByUser(userEntity).stream().max(Comparator.comparing(UserVerificationEntity::getCreationDate));

        if (!passwordEncoder.matches(request.getVerificationCode(), userVerificationEntity.orElseThrow(() -> new UserVerificationEntityNotFoundException(USER_VERIFICATION_ENTITY_NOT_FOUND.getMessage())).getEncryptedCode())) {
            throw new VerificationCodeIsNotMatchedException(VERIFICATION_CODE_IS_NOT_MATCHED.getMessage());
        }

        userEntity.setVerified(true);
        userRepository.save(userEntity);
        userRepository.flush();
    }

    @Override
    public Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException(USER_NOT_FOUND.getMessage());
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new UserPasswordIsNotMatchedException(USER_PASSWORD_IS_NOT_MATCHED.getMessage());
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return JwtUtils.generateJWT(authentication);
    }

    @Override
    public void startPasswordResetProcess(ResetPasswordPromptRequest request) throws UserNotFoundException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException(USER_NOT_FOUND.getMessage());
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        PasswordResetEntity passwordResetEntity = new PasswordResetEntity();
        passwordResetEntity.setUser(userEntity);
        passwordResetEntity.setCreationDate(LocalDateTime.now());

        String verificationCode = UUID.randomUUID().toString().substring(0, 5);
        passwordResetEntity.setEncryptedCode(passwordEncoder.encode(verificationCode));

        passwordResetRepository.save(passwordResetEntity);
        passwordResetRepository.flush();

        mailSender.sendPasswordResetMail(userEntity.getEmail(), verificationCode);
    }

    @Override
    public void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, PasswordResetEntityNotFoundException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException(USER_NOT_FOUND.getMessage());
        }

        UserEntity userEntity = userRepository.getUserEntityByUsername(request.getUsername());
        Optional<PasswordResetEntity> passwordResetEntity = passwordResetRepository.getAllByUser(userEntity).stream().max(Comparator.comparing(PasswordResetEntity::getCreationDate));

        if (!passwordEncoder.matches(request.getVerificationCode(), passwordResetEntity.orElseThrow(() -> new PasswordResetEntityNotFoundException(PASSWORD_RESET_ENTITY_NOT_FOUND.getMessage())).getEncryptedCode())) {
            throw new VerificationCodeIsNotMatchedException(VERIFICATION_CODE_IS_NOT_MATCHED.getMessage());
        }

        userEntity.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(userEntity);
        userRepository.flush();
    }

}
