package uz.pdp.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.dto.UserRegisterDto;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register/process")
    public String register(@RequestParam String email,
                           @RequestParam String password,
                           HttpSession session) {
        UserRegisterDto userRegisterDto =
                new UserRegisterDto(email, password);
        session.setAttribute("user", userRegisterDto);
        return "redirect:/verify";
    }
}
