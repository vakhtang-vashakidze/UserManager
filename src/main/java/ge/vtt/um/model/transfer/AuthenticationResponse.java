package ge.vtt.um.model.transfer;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class AuthenticationResponse extends GeneralResponse {
    private String accessToken;
    private String refreshToken;
}
