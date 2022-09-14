package ge.vtt.um.service.impl;

import ge.vtt.um.component.utils.JwtUtils;
import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.model.request.GeneralRequest;
import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.service.UserService;
import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

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
    public Map<String, String> performAuthentication(GeneralRequest request) throws UserNotFoundException {
        if (!userRepository.existsByUsername(request.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtUtils.generateJWT(authentication);
    }

    @Override
    public void startPasswordResetProcess(ResetPasswordPromptRequest request) {

    }

    @Override
    public void finalizePasswordResetProcess(ResetPasswordVerifyRequest request) {

    }

}
