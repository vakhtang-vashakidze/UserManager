package ge.vtt.um.service;


import ge.vtt.um.exception.UserAlreadyExistsException;
import ge.vtt.um.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.UserDTO;

public interface UserService {

    void performRegistration(UserDTO userDTO) throws UserAlreadyExistsException;

    void performAuthentication(UserDTO userDTO) throws UserNotFoundException;
    UserDTO getUserByUsername(String username) throws UserNotFoundException;
}
