package ge.vakhtang.um.model.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class GeneralResponse {
    private String message;
    private int status;
}
