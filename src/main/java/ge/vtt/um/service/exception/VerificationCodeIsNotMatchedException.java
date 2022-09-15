package ge.vtt.um.service.exception;

public class VerificationCodeIsNotMatchedException extends Exception {
    public VerificationCodeIsNotMatchedException(String message) {
        super(message);
    }

    public VerificationCodeIsNotMatchedException() {
        super();
    }
}
