package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import uz.pdp.project.entity.Task;
import uz.pdp.project.service.CommentService;
import uz.pdp.project.service.TaskService;
import uz.pdp.project.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final CommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/task")
    public String getAllTasks(Model model) {
        List<Task> allTasks = taskService.getAllTasks();
        return "task";
    }
}
