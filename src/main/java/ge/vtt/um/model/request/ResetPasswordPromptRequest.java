package ge.vtt.um.model.request;

import lombok.Data;

@Data
public class ResetPasswordPromptRequest {
    private String username;
    private String newPassword;
}
