package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.project.entity.Task;
import uz.pdp.project.entity.User;
import uz.pdp.project.service.TaskService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public String getTaskActiveTrue(Model model) {
        List<Task> activeStatusTasks = taskService.getActiveStatusTasks();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            User user = (User) principal;
            model.addAttribute("user", user);
        }
        model.addAttribute("activeStatusTasks", activeStatusTasks);
        return "task";
    }


}
