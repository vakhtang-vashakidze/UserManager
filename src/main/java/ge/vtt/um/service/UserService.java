package ge.vtt.um.service;


import ge.vtt.um.model.transfer.UserDTO;

public interface UserService {

    void performRegistration(UserDTO userDTO);

    UserDTO getUserByUsername(String username);
}
