package ge.vtt.um.service.impl;

import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.exception.UserAlreadyExistsException;
import ge.vtt.um.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.UserDTO;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Primary
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
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
    public void performAuthentication(UserDTO userDTO) throws UserNotFoundException {
        if (!userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }

    }

    @Override
    public UserDTO getUserByUsername(String username) throws UserNotFoundException {
        if (!userRepository.existsByUsername(username)) {
            throw new UserNotFoundException("User with provided details does not exist!");
        }
        UserEntity userEntity = userRepository.getUserEntityByUsername(username);
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(userEntity.getUsername());
        userDTO.setFirstname(userEntity.getFirstname());
        userDTO.setLastname(userEntity.getLastname());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPassword(userEntity.getPassword());
        return userDTO;
    }
}
