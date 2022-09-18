package ge.vtt.um.service.impl;

import ge.vtt.um.service.UMMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UMMailSenderImpl implements UMMailSender {

    private final Environment environment;

    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendRegisterVerificationMail(String email, String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailSender.getUsername());
        mailMessage.setTo(email);
        mailMessage.setText(String.format(environment.getProperty("template.user.register.verification"), verificationCode));
        mailMessage.setSubject("User verification");
        mailSender.send(mailMessage);
    }

    @Override
    public void sendPasswordResetMail(String email, String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailSender.getUsername());
        mailMessage.setTo(email);
        mailMessage.setText(String.format(environment.getProperty("template.password.reset.prompt"), verificationCode));
        mailMessage.setSubject("Reset password");
        mailSender.send(mailMessage);
    }
}
