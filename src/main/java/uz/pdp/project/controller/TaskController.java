package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.entity.Status;
import uz.pdp.project.entity.Task;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.AttachmentRepository;
import uz.pdp.project.repo.StatusRepository;
import uz.pdp.project.service.StatusService;
import uz.pdp.project.service.TaskService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final StatusService statusService;
    private final AttachmentRepository attachmentRepository;
    private final StatusRepository statusRepository;

    @GetMapping
    public String viewBoard(Model model) {

        List<Status> statuses = statusRepository.findAllByActiveTrueOrderByPositionNumber();
        model.addAttribute("statusOrdered", statuses);

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Task> allTasks = taskService.getAllTasks();
        model.addAttribute("allTasks", allTasks);

        int minPosition = statuses.stream().mapToInt(Status::getPositionNumber).min().orElse(Integer.MAX_VALUE);
        int maxPosition = statuses.stream().mapToInt(Status::getPositionNumber).max().orElse(Integer.MIN_VALUE);
        model.addAttribute("minPosition", minPosition);
        model.addAttribute("maxPosition", maxPosition);

        model.addAttribute("user", user);
        return "task";
    }
}
