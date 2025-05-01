package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.entity.Status;
import uz.pdp.project.repo.StatusRepository;
import uz.pdp.project.service.StatusService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;
    private final StatusRepository statusRepository;

    @GetMapping("/addStatus")
    public String addStatus(Model model) {
        List<Status> all = statusService.getAll();
        if (all.isEmpty()) {
            model.addAttribute("maxPosition", 1);
        } else {
            model.addAttribute("maxPosition",
                    all.size() + 1);
        }
        return "AddStatus";
    }

    @PostMapping("/save")
    public String saveStatus(Model model, @ModelAttribute Status status) {
        status.setActive(true);
        statusRepository.save(status);
        return "redirect:/task";
    }


}
