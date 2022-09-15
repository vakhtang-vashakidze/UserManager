package ge.vtt.um.service.exception;

public class UserPasswordIsNotMatchedException extends Exception {
    public UserPasswordIsNotMatchedException(String message) {
        super(message);
    }

    public UserPasswordIsNotMatchedException() {
        super();
    }
}
