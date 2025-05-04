package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.entity.Comment;
import uz.pdp.project.entity.Task;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.CommentRepository;
import uz.pdp.project.repo.TaskRepository;
import uz.pdp.project.repo.UserRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final TaskRepository taskRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;


    @GetMapping("/{id}")
    public String comments(@PathVariable Integer id, Model model) {
        model.addAttribute("task", taskRepository.findById(id).get());
        return "comments";
    }

    @PostMapping("/save/{id}")
    public String saveComments(@PathVariable Integer id, @RequestParam String text, Model model, Principal principal) {
        Comment comment = new Comment();
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();
        comment.setContent(text);
        comment.setDate(LocalDateTime.now());
        comment.setUser(user);
        commentRepository.save(comment);
        Optional<Task> byId = taskRepository.findById(id);
        if (byId.isPresent()) {
            Task task = byId.get();
            task.getComment().add(comment);
            taskRepository.save(task);
            model.addAttribute("task", task);
            return "comments";
        }
        return "redirect:/";
    }
}
