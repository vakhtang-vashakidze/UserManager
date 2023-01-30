package ge.vakhtang.um.service.exception;

public class UserVerificationEntityNotFoundException extends Exception{

    public UserVerificationEntityNotFoundException(String message) {
        super(message);
    }

    public UserVerificationEntityNotFoundException() {
        super();
    }
}
