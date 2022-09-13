package ge.vtt.um.service.impl;

import ge.vtt.um.component.utils.JwtUtils;
import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.UserDTO;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.service.UserService;
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
    public void performRegistration(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("User already exists!");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setFirstname(userDTO.getFirstname());
        userEntity.setLastname(userDTO.getLastname());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userEntity);
        userRepository.flush();
    }

    @Override
    public Map<String, String> performAuthentication(UserDTO userDTO) throws UserNotFoundException {
        if (!userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        return jwtUtils.generateJWT(authentication);
    }

}
