package ge.vakhtang.um.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class UserVerificationEntity {

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
