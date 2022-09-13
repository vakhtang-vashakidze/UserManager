package ge.vtt.um.controller;

import ge.vtt.um.model.UserDTO;
import ge.vtt.um.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PutMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
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