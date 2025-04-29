package uz.pdp.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.service.UserService;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String email,  @RequestParam String password, HttpSession session) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(email);
        userRegisterDto.setPassword(password);
        userService.register(userRegisterDto);
        session.setAttribute("user", userRegisterDto);
        return "redirect:/verify";
    }
}
