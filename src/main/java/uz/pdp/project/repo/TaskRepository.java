package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.project.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}