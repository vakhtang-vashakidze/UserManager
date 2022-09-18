package ge.vtt.um.service;

public interface UMMailSender {

    void sendRegisterVerificationMail(String email, String verificationCode);

    void sendPasswordResetMail(String email, String verificationCode);
}
