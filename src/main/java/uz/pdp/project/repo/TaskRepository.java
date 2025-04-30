package uz.pdp.project.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.project.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    @Query("SELECT t FROM Task t WHERE t.status.active = true ORDER BY t.status.positionNumber")
    List<Task> findAllByStatusActiveTrueOrderByStatusPositionNumber();
}