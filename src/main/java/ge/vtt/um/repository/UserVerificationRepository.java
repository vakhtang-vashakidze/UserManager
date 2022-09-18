package ge.vtt.um.repository;

import ge.vtt.um.entity.UserEntity;
import ge.vtt.um.entity.UserVerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserVerificationRepository extends JpaRepository<UserVerificationEntity, Integer> {

    List<UserVerificationEntity> getAllByUser(UserEntity userEntity);
}
