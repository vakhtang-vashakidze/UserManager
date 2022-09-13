package ge.vtt.um.service.impl;

import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.model.UserDTO;
import ge.vtt.um.repository.UserRepository;
import ge.vtt.um.service.UserService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void performRegistration(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(userDTO.getUsername());
        userEntity.setFirstname(userDTO.getFirstname());
        userEntity.setLastname(userDTO.getLastname());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(userDTO.getPassword());
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        UserEntity userEntity = userRepository.getUserEntityByUsername(username);
        UserDTO userDTO = new UserDTO();
        if (userEntity != null) {
            userDTO.setUsername(userEntity.getUsername());
            userDTO.setFirstname(userEntity.getFirstname());
            userDTO.setLastname(userEntity.getLastname());
            userDTO.setEmail(userEntity.getEmail());
            userDTO.setPassword(userEntity.getPassword());
        }
        return userDTO;
    }
}
