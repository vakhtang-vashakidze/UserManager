package ge.vtt.um.service;


import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.UserDTO;

import java.util.Map;

public interface UserService {

    void performRegistration(UserDTO userDTO) throws UserAlreadyExistsException;

    Map<String, String> performAuthentication(UserDTO userDTO) throws UserNotFoundException;
}
