package ge.vakhtang.um.repository;

import ge.vakhtang.um.entity.PasswordResetEntity;
import ge.vakhtang.um.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordResetEntity, Integer> {
    List<PasswordResetEntity> getAllByUser(UserEntity userEntity);
}
