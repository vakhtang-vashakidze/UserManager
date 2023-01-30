package ge.vakhtang.um.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordVerifyRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
    @NotBlank
    private String verificationCode;
}
