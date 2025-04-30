package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.Status;

import java.util.List;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    List<Status> findByActiveTrue();
}