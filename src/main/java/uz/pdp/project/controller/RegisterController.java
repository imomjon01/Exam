package uz.pdp.project.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.service.UserService;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    /** GET  /register  –  Forma ko‘rsatish */
    @GetMapping("/register")
    public String showForm(Model model) {
        model.addAttribute("user", new UserRegisterDto());   // form-backing bean
        return "register";                                   // templates/register.html
    }

    /** POST /register  –  Formani qabul qilish */
    @PostMapping("/register")
    public String processForm(@Valid @ModelAttribute("user") UserRegisterDto dto,
                              BindingResult errors,
                              HttpSession session,
                              Model model) {

        // 1. Frontend validatsiya xatolari
        if (errors.hasErrors()) {
            return "register";
        }

        try {
            // 2. Foydalanuvchini saqlash va verificationCode olish
            String code = userService.register(dto);

            // 3. Kod va e-mailni sessiyada vaqtincha saqlaymiz
            session.setAttribute("PENDING_EMAIL", dto.getEmail());
            session.setAttribute("PENDING_CODE",  code);

            // 4. Keyingi bosqich: /verify sahifasiga yo‘naltiriladi
            return "redirect:/verify";

        } catch (IllegalStateException | IllegalArgumentException ex) {
            // Masalan: e-mail band, attachment topilmadi va hokazo
            model.addAttribute("error", ex.getMessage());
            return "register";
        }
    }
}
