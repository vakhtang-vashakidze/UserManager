package ge.vtt.um.controller;

import ge.vtt.um.model.transfer.UserDTO;
import ge.vtt.um.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<String> register(@Valid @RequestBody UserDTO userDTO) {
        log.info("Request body : {}", userDTO);
        userService.performRegistration(userDTO);
        return ResponseEntity.ok("registered!");
    }

    @GetMapping("/getBy/username/{username}")
    public ResponseEntity<UserDTO> getUserByUsername(@PathVariable String username) {
        log.info("Request param : {}", username);
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
}
