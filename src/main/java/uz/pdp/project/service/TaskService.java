package uz.pdp.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.project.entity.Task;
import uz.pdp.project.repo.TaskRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }


    public List<Task> getActiveStatusTasks() {
        return taskRepository.findByStatusActiveTrue();
    }
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}

