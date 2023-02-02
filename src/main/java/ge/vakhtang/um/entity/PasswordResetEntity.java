package ge.vakhtang.um.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "PASSWORD_RESETS")
@Data
@ToString
public class PasswordResetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    private String encryptedCode;

    @NotNull
    private LocalDateTime creationDate;

    @ManyToOne
    private UserEntity user;
}
