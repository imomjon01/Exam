package uz.pdp.project.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.Roles;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.RoleRepository;
import uz.pdp.project.repo.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        List<Role> all = roleRepository.findAll();
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            User user = new User();
            user.setId(null);
            user.setEmail("imomjonuzb@gmail.com");
            user.setPassword(passwordEncoder.encode("imomjonuzb"));
            user.setRoles(List.of());
        }

        if (all.isEmpty()) {
            all.add(new Role(null, Roles.PROGRAMMER.name()));
            all.add(new Role(null, Roles.ADMIN.name()));
            all.add(new Role(null, Roles.MAINTAINER.name()));
            roleRepository.saveAll(all);

            if (users.isEmpty()) {

                User user = new User();
                user.setId(null);
                user.setEmail("imomjonuzb@gmail.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(all);
                userRepository.save(user);
                User user1 = new User();
                user.setId(null);
                user.setEmail("mirzabeknormakhmatov03@gmail.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(all);
                userRepository.save(user1);
                User user2 = new User();
                user.setId(null);
                user.setEmail("dovudtoshxojaev@gmail.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(all);
                userRepository.save(user2);
            }
        }


    }

}
