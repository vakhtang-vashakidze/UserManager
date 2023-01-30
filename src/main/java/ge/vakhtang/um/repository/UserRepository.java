package ge.vakhtang.um.repository;

import ge.vakhtang.um.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity getUserEntityByUsername(String username);

    boolean existsByUsername(String username);
}
