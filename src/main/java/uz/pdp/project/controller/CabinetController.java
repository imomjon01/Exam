package uz.pdp.project.controller;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.project.entity.Attachment;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.AttachmentRepository;
import uz.pdp.project.repo.UserRepository;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/cabinet")
public class CabinetController {
    private final UserRepository userRepository;
    private final AttachmentRepository attachmentRepository;

    @GetMapping
    public String cabinet(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "cabinet";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update")
    @Transactional
    public String updateUser(@RequestParam("file") MultipartFile file) throws IOException {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!file.isEmpty()) {
            // Yangi faylni saqlaymiz
            Attachment attachment = new Attachment();
            attachment.setFileName(file.getOriginalFilename());
            attachment.setFileType(file.getContentType());
            attachment.setContent(file.getBytes());

            Attachment savedAttachment = attachmentRepository.save(attachment);

            // Foydalanuvchining attachment maydonini yangilaymiz
            currentUser.setAttachment(savedAttachment);
        }

        userRepository.save(currentUser);
        return "redirect:/cabinet";
    }


}
