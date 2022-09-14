package ge.vtt.um.model.request;

import lombok.Data;

@Data
public class ResetPasswordVerifyRequest {
    private String username;
    private String password;
    private String verificationCode;
}
