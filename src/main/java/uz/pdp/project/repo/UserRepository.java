package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsByEmail(String email);
}

