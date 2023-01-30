package ge.vakhtang.um.service.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {

    PASSWORD_RESET_ENTITY_NOT_FOUND("Password reset entity was not found!"),
    USER_ALREADY_EXISTS("User already exists!"),
    USER_NOT_FOUND("User with provided details does not exist!"),
    USER_PASSWORD_IS_NOT_MATCHED("Provided password for user is not matched!"),
    USER_VERIFICATION_ENTITY_NOT_FOUND("User verification entity was not found!"),
    VERIFICATION_CODE_IS_NOT_MATCHED("Provided verification code is not matched!");


    private final String message;

    ExceptionMessages(String message){
        this.message = message;
    }
}
