package ge.vtt.um.model.transfer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GeneralResponse {
    private String message;
    private int status;
}
