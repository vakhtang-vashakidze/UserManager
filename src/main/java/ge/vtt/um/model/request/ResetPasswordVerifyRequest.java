package ge.vtt.um.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordVerifyRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String verificationCode;
}
