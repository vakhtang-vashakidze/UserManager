package ge.vtt.um.model;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
