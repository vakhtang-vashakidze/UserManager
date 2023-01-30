package ge.vakhtang.um.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ResetPasswordPromptRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String newPassword;
}
