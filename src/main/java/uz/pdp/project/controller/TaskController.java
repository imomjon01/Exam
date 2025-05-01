package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final StatusService statusService;
    private final AttachmentRepository attachmentRepository;

    /** 1) Show the board with the form closed **/
    @GetMapping
    public String viewBoard(Model model) {
        populateModel(model, false);
        return "task";
    }

    /** 2) Show the board with the form already open **/
    @GetMapping("/new")
    public String newTaskForm(Model model) {
        populateModel(model, true);
        return "task";
    }

    private void populateModel(Model model, boolean openForm) {
        // All statuses (template will show only active ones)
        List<Status> statuses = statusService.getAll();
        model.addAttribute("statuses", statuses);

        // All tasks
        List<Task> tasks = taskService.getAllTasks();
        model.addAttribute("tasks", tasks);

        // Blank Task + non-null Status for form-binding
        Task newTask = new Task();
        newTask.setStatus(new Status());
        model.addAttribute("newTask", newTask);

        // Flag so Thymeleaf can add "show" to the collapse if needed
        model.addAttribute("openForm", openForm);
    }

    /** 3) Create a new Task **/
    @PostMapping("/tasks")
    public String addTask(
            @ModelAttribute("newTask") Task task,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication auth
    ) throws IOException {
        // Lookup real Status by ID
        Integer statusId = task.getStatus().getId();
        task.setStatus(statusService.getById(statusId));

        // Assign current user
        User me = (User) auth.getPrincipal();
        task.setUser(me);

        // Handle optional file upload
        if (file != null && !file.isEmpty()) {
            Attachment att = new Attachment();
            att.setFileName(file.getOriginalFilename());
            att.setFileType(file.getContentType());
            att.setContent(file.getBytes());  // @Lob
            attachmentRepository.save(att);
            task.setAttachment(att);
        }

        taskService.saveTask(task);
        return "redirect:/task";
    }

    /** 4) Delete a Task **/
    @PostMapping("/tasks/{id}/delete")
    public String deleteTask(@PathVariable Integer id) {
        taskService.deleteTask(id);
        return "redirect:/task";
    }

    @PostMapping("/tasks/{id}/move")
    public String moveTask(
            @PathVariable Integer id,
            @RequestParam String dir
    ) {
        Task task = taskService.getTaskById(id);
        List<Status> statuses = statusService.getAll();
        int idx = statuses.indexOf(task.getStatus());
        int max = statuses.size() - 1;
        int newIdx = "right".equals(dir)
                ? Math.min(idx + 1, max)
                : Math.max(idx - 1, 0);
        task.setStatus(statuses.get(newIdx));
        taskService.saveTask(task);
        return "redirect:/task";
    }

    @GetMapping("/attachments/{id}")
    public ResponseEntity<byte[]> serveAttachment(@PathVariable Integer id) {
        Attachment att = attachmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No such attachment: " + id));
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(att.getFileType()))
                .body(att.getContent());
    }
}
