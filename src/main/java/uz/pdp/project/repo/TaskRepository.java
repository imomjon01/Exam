package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByStatusActiveTrue();
}