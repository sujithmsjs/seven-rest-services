package tech.suji.seven_prods.projects.usermgnt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import tech.suji.seven_prods.projects.usermgnt.entity.User;


public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsernameIgnoreCase(String username);

    @Modifying
    @Query("UPDATE User u SET u.isEnabled = :isEnabled WHERE u.id = :userId")
    int updateStatusById(@Param("userId") Long userId, @Param("isEnabled") boolean isEnabled);
}
