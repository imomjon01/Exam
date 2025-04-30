package uz.pdp.project.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.Roles;
import uz.pdp.project.service.RoleService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        List<Role> allRoles = roleService.getAllRoles();
        if (allRoles.isEmpty()) {
            allRoles.add(new Role(null, Roles.PROGRAMMER.name()));
            allRoles.add(new Role(null, Roles.MAINTAINER.name()));
            allRoles.add(new Role(null, Roles.ADMIN.name()));
            roleService.saveAll(allRoles);
        }
    }
}
