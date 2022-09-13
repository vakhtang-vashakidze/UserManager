package ge.vtt.um.controller.advice;

import ge.vtt.um.exception.UserAlreadyExistsException;
import ge.vtt.um.exception.UserNotFoundException;
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
    public Map<String, String> handleUserAlreadyExistsException(UserAlreadyExistsException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("status", String.valueOf(HttpStatus.CONFLICT.value()));
        response.put("message", exception.getMessage());
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public Map<String, String> handleUserNotFoundException(UserNotFoundException exception) {
        Map<String, String> response = new HashMap<>();
        response.put("status", String.valueOf(HttpStatus.NOT_FOUND.value()));
        response.put("message", exception.getMessage());
        return response;
    }
}
