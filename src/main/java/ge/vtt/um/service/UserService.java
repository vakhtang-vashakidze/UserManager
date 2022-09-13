package ge.vtt.um.service;


import ge.vtt.um.model.UserDTO;

public interface UserService {

    void performRegistration(UserDTO userDTO);

    UserDTO getUserByUsername(String username);
}
