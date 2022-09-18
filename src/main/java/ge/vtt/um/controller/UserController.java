package ge.vtt.um.controller;

import ge.vtt.um.model.request.GeneralRequest;
import ge.vtt.um.model.request.RegisterVerifyRequest;
import ge.vtt.um.model.request.ResetPasswordPromptRequest;
import ge.vtt.um.model.request.ResetPasswordVerifyRequest;
import ge.vtt.um.model.response.AuthenticationResponse;
import ge.vtt.um.model.response.GeneralResponse;
import ge.vtt.um.service.UserService;
import ge.vtt.um.service.exception.*;
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
    public ResponseEntity<GeneralResponse> register(@Valid @RequestBody GeneralRequest request) throws UserAlreadyExistsException {
        log.info("Request body : {}", request);
        userService.performRegistration(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("Registration completed successfully!")
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @PostMapping("/register/verify")
    public ResponseEntity<GeneralResponse> verifyRegistration(@Valid @RequestBody RegisterVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, UserVerificationEntityNotFoundException {
        log.info("Request body : {}", request);

        userService.performRegisterVerification(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("User verification completed successfully!")
                .status(HttpStatus.OK.value())
                .build()));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<GeneralResponse> authenticate(@Valid @RequestBody GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException {
        log.info("Request body : {}", request);
        Map<String, String> tokens = userService.performAuthentication(request);

        return ResponseEntity.of(Optional.of(AuthenticationResponse.builder()
                .accessToken(tokens.get("accessToken"))
                .refreshToken(tokens.get("refreshToken"))
                .message("Registration completed successfully!")
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @PostMapping("/password/reset/prompt")
    public ResponseEntity<GeneralResponse> resetPasswordPrompt(@Valid @RequestBody ResetPasswordPromptRequest request) throws UserNotFoundException {
        log.info("Request body : {}", request);
        userService.startPasswordResetProcess(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("Password reset started successfully!")
                .status(HttpStatus.OK.value())
                .build()));
    }

    @PostMapping("/password/reset/verify")
    public ResponseEntity<GeneralResponse> resetPasswordVerification(@Valid @RequestBody ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, PasswordResetEntityNotFoundException {
        log.info("Request body : {}", request);
        userService.finalizePasswordResetProcess(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message("Password reset completed successfully!")
                .status(HttpStatus.OK.value())
                .build()));
    }
}
