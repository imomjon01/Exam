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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusController {
    private final StatusService statusService;

    @GetMapping("/addStatus")
    public String addStatus(Model model) {
        List<Status> all = statusService.getAll();

        int maxPosition = all.stream().mapToInt(Status::getPositionNumber).max().orElse(Integer.MIN_VALUE);
        model.addAttribute("status", new Status());
        model.addAttribute("maxPosition", maxPosition + 1);

        return "AddStatus";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Status status) {
        statusService.save(status);
        return "redirect:/task";
    }
}
