package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    @GetMapping
    public String admin(Model model) {
        List<User> all = userRepository.findAll();

      //  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", all);
        return "admin";
    }

}
