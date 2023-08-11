package home.todayhome.repository;

import home.todayhome.entity.User;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    boolean existsByEmail(String email);

    User findByEmail(String email);
}
