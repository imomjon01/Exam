package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.project.entity.Attachment;
import uz.pdp.project.entity.Status;
import uz.pdp.project.entity.Task;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.AttachmentRepository;
import uz.pdp.project.repo.StatusRepository;
import uz.pdp.project.repo.TaskRepository;
import uz.pdp.project.repo.UserRepository;
import uz.pdp.project.service.StatusService;
import uz.pdp.project.service.TaskService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final UserRepository userRepository;
    private final TaskService taskService;
    private final StatusService statusService;
    private final AttachmentRepository attachmentRepository;
    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;

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

    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN')")
    @GetMapping("/addTaskPage")
    public String addTaskPage(Model model) {
        model.addAttribute("allUsers", userRepository.findAll());
        List<Status> all = statusRepository.findAll();
        if (all.isEmpty()) {
            return "redirect:/task";
        }
        model.addAttribute("allStatuses", all);
        return "addTask";
    }

    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN')")
    @PostMapping("/taskSave")
    public String taskSave(@RequestParam String title,
                           @RequestParam Integer userId,
                           @RequestParam Integer statusId,
                           @RequestParam(required = false) MultipartFile file) throws IOException {
        Task task = new Task();

        task.setTitle(title);
        userRepository.findById(userId).ifPresent(task::setUser);
        statusRepository.findById(statusId).ifPresent(task::setStatus);
        Attachment attachment = new Attachment();
        if (file != null && !file.isEmpty()) {
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setContent(file.getBytes());
        } else {
            ClassPathResource imgFile = new ClassPathResource("static/task.png");
            attachment.setFileName("task.png");
            attachment.setFileType("image/png");
            attachment.setContent(imgFile.getInputStream().readAllBytes());
        }
        task.setAttachment(attachment);
        task.setComment(new ArrayList<>());
        taskRepository.save(task);
        return "redirect:/task";
    }

    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN')")
    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allStatuses", statusRepository.findAll());
        return "editTask";
    }

    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN')")
    @PostMapping("/taskUpdate")
    public String updateTask(@ModelAttribute Task task,
                             @RequestParam("file") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setContent(file.getBytes());

            attachment = attachmentRepository.save(attachment);
            task.setAttachment(attachment);
        } else {
            Task existingTask = taskService.getTaskById(task.getId());
            task.setAttachment(existingTask.getAttachment());
        }

        taskService.saveTask(task);
        return "redirect:/task";
    }


    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN','PROGRAMMER')")
    @PostMapping("/move/{id}")
    public String move(@PathVariable Integer id) {
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            Status currentStatus = task.getStatus();
            int currentPosition = currentStatus.getPositionNumber();
            // Avvalgi statusni topish
            Optional<Status> previousStatusOpt = statusRepository
                    .moveRight(currentPosition);

            if (previousStatusOpt.isPresent()) {
                Status previousStatus = previousStatusOpt.get();
                task.setStatus(previousStatus);
                taskRepository.save(task);
            }
        }
        return "redirect:/task"; // Foydalanuvchini task ro'yxatiga yo'naltirish
    }

    @PreAuthorize("hasAnyRole('MAINTAINER','ADMIN','PROGRAMMER')")
    @PostMapping("/moveBack/{id}")
    public String moveBack(@PathVariable Integer id) {
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            Status currentStatus = task.getStatus();
            int currentPosition = currentStatus.getPositionNumber();
            // Avvalgi statusni topish
            Optional<Status> previousStatusOpt = statusRepository
                    .moveLeft(currentPosition);

            if (previousStatusOpt.isPresent()) {
                Status previousStatus = previousStatusOpt.get();
                task.setStatus(previousStatus);
                taskRepository.save(task);
            }
        }
        return "redirect:/task"; // Foydalanuvchini task ro'yxatiga yo'naltirish
    }
}


