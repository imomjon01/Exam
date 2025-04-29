package uz.pdp.project.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.Roles;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRole(Roles role);   // aynan shunday bo‘lishi kerak
}



