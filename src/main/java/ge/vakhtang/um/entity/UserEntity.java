package ge.vakhtang.um.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity(name = "USERS")
@Data
@ToString
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String username;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;

    private boolean verified;

    @OneToMany
    private List<PasswordResetEntity> passwordResetEntities;

    @OneToMany
    private List<UserVerificationEntity> userVerificationEntities;

    @OneToOne(fetch = FetchType.EAGER)
    private RoleEntity role;
}
