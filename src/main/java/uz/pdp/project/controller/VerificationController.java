package uz.pdp.project.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.Roles;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.RoleRepository;
import uz.pdp.project.repo.UserRepository;
import uz.pdp.project.service.EmailSender;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class VerificationController {
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping("/verify")
    public String verification(HttpSession session) {
        String randomCode = emailSender.randomCode();
        UserRegisterDto user = (UserRegisterDto) session.getAttribute("user");
        String email = user.getEmail();
        emailSender.sendEmail(email, randomCode);
        user.setRandomCode(randomCode);
        session.setAttribute("user", user);
        return "/verification";
    }

    @PostMapping("/verify/process")
    public String process(@RequestParam String code, HttpSession session) {
        UserRegisterDto user = (UserRegisterDto) session.getAttribute("user");
        if (code.equals(user.getRandomCode())) {
            User registeredUser = new User();
            registeredUser.setEmail(user.getEmail());
            registeredUser.setPassword(passwordEncoder.encode(user.getPassword()));
            registeredUser.setVerificationCode(true);
            Role role=roleRepository.findByRole(Roles.PROGRAMMER.name());
            List<Role> roles=new ArrayList<>();
            roles.add(role);
            registeredUser.setRoles(roles);
            userRepository.save(registeredUser);
        }
        return "redirect:/login";
    }

}
