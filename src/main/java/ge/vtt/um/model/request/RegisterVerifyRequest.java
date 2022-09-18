package ge.vtt.um.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RegisterVerifyRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String verificationCode;
}
