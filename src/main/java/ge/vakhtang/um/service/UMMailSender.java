package ge.vakhtang.um.service;

public interface UMMailSender {

    void sendRegisterVerificationMail(String email, String verificationCode);

    void sendPasswordResetMail(String email, String verificationCode);
}
