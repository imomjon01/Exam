package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.RoleRepository;
import uz.pdp.project.repo.UserRepository;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @GetMapping
    public String admin(Model model) {
        List<User> all = userRepository.findAll();

      //  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("users", all);
        return "admin";
    }

    @GetMapping("/change-role/{id}")
    public String showRoleForm(@PathVariable Integer id, Model model) {
        User user = userRepository.findById(id).orElseThrow();
        List<Role> allRoles = roleRepository.findAll();
        model.addAttribute("user", user);
        model.addAttribute("roles", allRoles);
        return "change-role";
    }

    @PostMapping("/change-role/{id}")
    public String updateUserRoles(@PathVariable Integer id,
                                  @RequestParam("roleIds") List<Integer> roleIds) {
        User user = userRepository.findById(id).orElseThrow();
        List<Role> selectedRoles = roleRepository.findAllById(roleIds);
        user.setRoles(selectedRoles); // eski rollarni almashtiramiz
        userRepository.save(user);
        return "redirect:/admin";
    }





}
