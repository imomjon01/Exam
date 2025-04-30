package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.project.entity.Attachment;
import uz.pdp.project.entity.Status;
import uz.pdp.project.entity.Task;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.AttachmentRepository;
import uz.pdp.project.service.StatusService;
import uz.pdp.project.service.TaskService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final StatusService statusService;
    private final AttachmentRepository attachmentRepository;

    @GetMapping
    public String viewBoard(Model model) {
        // Load statuses for the dropdown
        List<Status> statuses = statusService.getActiveStatusesOrdered();
        model.addAttribute("statuses", statuses);

        // Load and partition tasks
        List<Task> all = taskService.getAllTasks();
        model.addAttribute("openTasks", all.stream()
                .filter(t -> "OPEN".equals(t.getStatus().getName()))
                .collect(Collectors.toList()));
        model.addAttribute("inProgressTasks", all.stream()
                .filter(t -> "IN_PROGRESS".equals(t.getStatus().getName()))
                .collect(Collectors.toList()));
        model.addAttribute("completeTasks", all.stream()
                .filter(t -> "COMPLETE".equals(t.getStatus().getName()))
                .collect(Collectors.toList()));

        // ← Initialize newTask with a non-null Status to avoid NPE
        Task newTask = new Task();
        newTask.setStatus(new Status());
        model.addAttribute("newTask", newTask);

        return "task";  // Thymeleaf template: resources/templates/task.html
    }

    @PostMapping("/tasks")
    public String addTask(@ModelAttribute("newTask") Task task,
                          @RequestParam(value = "file", required = false) MultipartFile file,
                          Authentication auth) throws IOException {

        // Lookup full Status entity by ID
        Integer statusId = task.getStatus().getId();
        task.setStatus(statusService.getById(statusId));

        // Set current user
        User me = (User) auth.getPrincipal();
        task.setUser(me);

        // Handle attachment if present
        if (file != null && !file.isEmpty()) {
            Attachment att = new Attachment();
            att.setFileName(file.getOriginalFilename());
            att.setFileType(file.getContentType());
            att.setContent(file.getBytes());
            attachmentRepository.save(att);
            task.setAttachment(att);
        }

        taskService.saveTask(task);
        return "redirect:/task";
    }

    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return "redirect:/task";
    }

    @PostMapping("/tasks/{id}/move")
    public String moveTask(@PathVariable Integer id,
                           @RequestParam String dir) {
        Task task = taskService.getTaskById(id);
        List<Status> statuses = statusService.getActiveStatusesOrdered();
        int idx = statuses.indexOf(task.getStatus());
        int max = statuses.size() - 1;
        int newIdx = "right".equals(dir)
                ? Math.min(idx + 1, max)
                : Math.max(idx - 1, 0);
        task.setStatus(statuses.get(newIdx));
        taskService.saveTask(task);
        return "redirect:/task";
    }
}
