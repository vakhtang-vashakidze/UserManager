package ge.vakhtang.um.service.exception;

public class PasswordResetEntityNotFoundException extends Exception {

    public PasswordResetEntityNotFoundException(String message) {
        super(message);
    }

    public PasswordResetEntityNotFoundException() {
        super();
    }
}
