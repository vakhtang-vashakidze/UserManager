package ge.vtt.um.controller.advice;

import ge.vtt.um.exception.UserAlreadyExistsException;
import ge.vtt.um.exception.UserNotFoundException;
import ge.vtt.um.model.transfer.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

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
    @ExceptionHandler(UserNotFoundException.class)
    public GeneralResponse handleUserNotFoundException(UserNotFoundException exception) {
        return GeneralResponse.builder()
                .message(exception.getMessage())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
    }
}
