package ge.vakhtang.um.controller.advice;

import ge.vakhtang.um.model.response.GeneralResponse;
import ge.vakhtang.um.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainAdvice {


    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public GeneralResponse handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        return GeneralResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({UsernameNotFoundException.class, UserNotFoundException.class, PasswordResetEntityNotFoundException.class, UserVerificationEntityNotFoundException.class})
    public GeneralResponse handleUserNotFoundException(Exception exception) {
        return GeneralResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(VerificationCodeIsNotMatchedException.class)
    public GeneralResponse handleVerificationCodeIsNotMatchedException(VerificationCodeIsNotMatchedException exception) {
        return GeneralResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserPasswordIsNotMatchedException.class)
    public GeneralResponse handleUserPasswordIsNotMatchedException(UserPasswordIsNotMatchedException exception) {
        return GeneralResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.CONFLICT.value())
                .build();
    }
}
