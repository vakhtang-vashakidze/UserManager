package ge.vtt.um.service.exception;

public class UserVerificationEntityNotFoundException extends Exception{

    public UserVerificationEntityNotFoundException(String message) {
        super(message);
    }

    public UserVerificationEntityNotFoundException() {
        super();
    }
}
