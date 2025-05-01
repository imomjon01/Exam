package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
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
import uz.pdp.project.repo.UserRepository;
import uz.pdp.project.service.StatusService;
import uz.pdp.project.service.TaskService;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final UserRepository userRepository;
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

    @GetMapping("/addTaskPage")
    public String addTaskPage(Model model) {
        model.addAttribute("task", new Task());
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allStatuses", statusRepository.findAll());
        return "addTask";
    }


    @PostMapping("/taskSave")
    public String taskSave(@ModelAttribute Task task,
                           @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setContent(file.getBytes());
            task.setAttachment(attachment);
        } else {
            ClassPathResource defaultImg = new ClassPathResource("static/img/default.png");
            byte[] bytes = Files.readAllBytes(defaultImg.getFile().toPath());

            Attachment defaultAttachment = new Attachment();
            defaultAttachment.setFileName("default.png");
            defaultAttachment.setFileType("image/png");
            defaultAttachment.setContent(bytes);

            task.setAttachment(defaultAttachment);
        }

        taskService.saveTask(task);
        return "redirect:/task";
    }

    @GetMapping("/edit/{id}")
    public String editTask(@PathVariable Integer id, Model model) {
        Task task = taskService.getTaskById(id);
        model.addAttribute("task", task);
        model.addAttribute("allUsers", userRepository.findAll());
        model.addAttribute("allStatuses", statusRepository.findAll());
        return "editTask";
    }

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


}
