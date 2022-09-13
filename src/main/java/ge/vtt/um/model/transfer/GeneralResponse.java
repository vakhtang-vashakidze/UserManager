package ge.vtt.um.model.transfer;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GeneralResponse {
    private String message;
    private int status;
}
