package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.entity.Status;
import uz.pdp.project.service.StatusService;

import java.util.List;

@Controller
@RequestMapping("/manage/orders")
@RequiredArgsConstructor
public class ManageOrdersController {
    private final StatusService statusService;

    @GetMapping
    public String manageOrders(Model model) {
        List<Status> all = statusService.getAll();
        model.addAttribute("allStatus", all);
        return "manage/orders";
    }

}
