package ge.vtt.um.controller;

import ge.vtt.um.service.exception.UserAlreadyExistsException;
import ge.vtt.um.service.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.AuthenticationResponse;
import ge.vtt.um.model.transfer.GeneralResponse;
import ge.vtt.um.model.transfer.UserDTO;
import ge.vtt.um.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("/register")
    public ResponseEntity<GeneralResponse> register(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistsException {
        log.info("Request body : {}", userDTO);
        userService.performRegistration(userDTO);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("Registration completed successfully!")
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<GeneralResponse> authenticate(@Valid @RequestBody UserDTO userDTO) throws UserNotFoundException {
        log.info("Request body : {}", userDTO);
        Map<String, String> tokens = userService.performAuthentication(userDTO);

        return ResponseEntity.of(Optional.of(AuthenticationResponse.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .message("Registration completed successfully!")
                .status(HttpStatus.CREATED.value())
                .build()));
    }
}
