package ge.vakhtang.um.service.impl;

import ge.vakhtang.um.service.UMMailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import static ge.vakhtang.um.component.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class UMMailSenderImpl implements UMMailSender {
    private final JavaMailSenderImpl mailSender;

    @Override
    public void sendRegisterVerificationMail(String email, String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailSender.getUsername());
        mailMessage.setTo(email);
        mailMessage.setText(String.format(USER_VERIFICATION_EMAIL_TEMPLATE, verificationCode));
        mailMessage.setSubject(USER_VERIFICATION_EMAIL_SUBJECT);
        mailSender.send(mailMessage);
    }

    @Override
    public void sendPasswordResetMail(String email, String verificationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailSender.getUsername());
        mailMessage.setTo(email);
        mailMessage.setText(String.format(PASSWORD_RESET_PROMPT_EMAIL_TEMPLATE, verificationCode));
        mailMessage.setSubject(PASSWORD_RESET_EMAIL_SUBJECT);
        mailSender.send(mailMessage);
    }
}
