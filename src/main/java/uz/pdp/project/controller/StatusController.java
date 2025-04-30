package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.pdp.project.entity.Status;
import uz.pdp.project.service.StatusService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/addStatus")
    public String addStatus(Model model) {
        model.addAttribute("status", new Status());
        return "add";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Status status) {
        statusService.save(status);
        return "redirect:/task";
    }
}
