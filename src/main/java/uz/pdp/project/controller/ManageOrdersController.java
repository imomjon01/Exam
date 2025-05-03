package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.dto.StatusListWrapper;
import uz.pdp.project.entity.Status;
import uz.pdp.project.service.StatusService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/manageOrders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'MAINTAINER')")
public class ManageOrdersController {
    private final StatusService statusService;
    @GetMapping
    public String manageOrders(Model model) {
        List<Status> all = statusService.getAll();
        if (all.isEmpty()) {
            return "redirect:/task";
        }
        StatusListWrapper wrapper = new StatusListWrapper();
        wrapper.setStatusList(all);
        model.addAttribute("wrapper", wrapper);
        return "manageOrders";
    }

    @PostMapping("/update")
    public String updateStatuses(@ModelAttribute StatusListWrapper wrapper, Model model) {
        List<Status> statuses = wrapper.getStatusList();

        Set<Integer> seenPositions = new HashSet<>();
        for (Status status : statuses) {
            Integer pos = status.getPositionNumber();
            if (pos != null) {
                if (!seenPositions.add(pos)) {
                    model.addAttribute("error", "Position numbers must be unique! Duplicate: " + pos);
                    model.addAttribute("wrapper", wrapper);
                    return "manageOrders";
                }
            }
        }
        statusService.updateAll(statuses);
        return "redirect:/task";
    }
}