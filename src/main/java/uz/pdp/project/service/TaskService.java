package uz.pdp.project.service;

import uz.pdp.project.entity.Task;
import java.util.List;

public interface TaskService {
    List<Task> getAllTasks();
    Task getTaskById(Integer id);
    Task saveTask(Task task);
    void deleteTask(Integer id);
}
