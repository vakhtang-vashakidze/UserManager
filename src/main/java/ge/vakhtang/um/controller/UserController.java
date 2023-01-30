package ge.vakhtang.um.controller;

import ge.vakhtang.um.model.request.GeneralRequest;
import ge.vakhtang.um.model.request.RegisterVerifyRequest;
import ge.vakhtang.um.model.request.ResetPasswordPromptRequest;
import ge.vakhtang.um.model.request.ResetPasswordVerifyRequest;
import ge.vakhtang.um.model.response.AuthenticationResponse;
import ge.vakhtang.um.model.response.GeneralResponse;
import ge.vakhtang.um.service.UserService;
import ge.vakhtang.um.service.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;
import java.util.Optional;

import static ge.vakhtang.um.component.utils.Constants.*;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PutMapping("/register")
    public ResponseEntity<GeneralResponse> register(@Valid @RequestBody GeneralRequest request) throws UserAlreadyExistsException {
        log.info(REQUEST_BODY_LOGGER_TEMPLATE, request);
        userService.performRegistration(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message(REGISTRATION_GREETING_MESSAGE)
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @PostMapping("/register/verify")
    public ResponseEntity<GeneralResponse> verifyRegistration(@Valid @RequestBody RegisterVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, UserVerificationEntityNotFoundException {
        log.info(REQUEST_BODY_LOGGER_TEMPLATE, request);

        userService.performRegisterVerification(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message(REGISTER_VERIFICATION_GREETING_MESSAGE)
                .status(HttpStatus.OK.value())
                .build()));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<GeneralResponse> authenticate(@Valid @RequestBody GeneralRequest request) throws UserNotFoundException, UserPasswordIsNotMatchedException {
        log.info(REQUEST_BODY_LOGGER_TEMPLATE, request);
        Map<String, String> tokens = userService.performAuthentication(request);

        return ResponseEntity.of(Optional.of(AuthenticationResponse.builder()
                .accessToken(tokens.get(ACCESS_TOKEN))
                .refreshToken(tokens.get(REFRESH_TOKEN))
                .message(AUTHENTICATION_GREETING_MESSAGE)
                .status(HttpStatus.CREATED.value())
                .build()));
    }

    @PostMapping("/password/reset/prompt")
    public ResponseEntity<GeneralResponse> resetPasswordPrompt(@Valid @RequestBody ResetPasswordPromptRequest request) throws UserNotFoundException {
        log.info(REQUEST_BODY_LOGGER_TEMPLATE, request);
        userService.startPasswordResetProcess(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message(PASSWORD_RESET_START_GREETING_MESSAGE)
                .status(HttpStatus.OK.value())
                .build()));
    }

    @PostMapping("/password/reset/verify")
    public ResponseEntity<GeneralResponse> resetPasswordVerification(@Valid @RequestBody ResetPasswordVerifyRequest request) throws UserNotFoundException, VerificationCodeIsNotMatchedException, PasswordResetEntityNotFoundException {
        log.info(REQUEST_BODY_LOGGER_TEMPLATE, request);
        userService.finalizePasswordResetProcess(request);

        return ResponseEntity.of(Optional.of(GeneralResponse.builder()
                .message(PASSWORD_RESET_COMPLETION_GREETING_MESSAGE)
                .status(HttpStatus.OK.value())
                .build()));
    }
}
