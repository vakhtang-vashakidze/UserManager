package ge.vtt.um.controller;

import ge.vtt.um.exception.UserAlreadyExistsException;
import ge.vtt.um.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.GeneralResponse;
import ge.vtt.um.model.transfer.UserDTO;
import ge.vtt.um.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/register")
    public ResponseEntity<GeneralResponse> register(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        log.info("Request body : {}", userDTO);
        userService.performRegistration(userDTO);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("Registration completed successfully!")
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @GetMapping("/getBy/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) throws UserNotFoundException {
        log.info("Request param : {}", username);
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
