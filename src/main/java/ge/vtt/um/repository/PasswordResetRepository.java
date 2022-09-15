package ge.vtt.um.repository;

import ge.vtt.um.entity.PasswordResetEntity;
import ge.vtt.um.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Integer> {

    List<PasswordResetEntity> getAllByUser(UserEntity userEntity);

}
