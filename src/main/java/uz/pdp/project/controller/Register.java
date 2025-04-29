package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import uz.pdp.project.repo.UserRepository;

@Controller
@RequiredArgsConstructor
public class Register {
    final UserRepository userRepository;

    @GetMapping("/register")
    public String register(){
        return "register";
    }


}
