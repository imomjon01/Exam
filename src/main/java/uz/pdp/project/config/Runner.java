package uz.pdp.project.config;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.entity.Attachment;
import uz.pdp.project.entity.Role;
import uz.pdp.project.entity.Roles;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.AttachmentRepository;
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
    private final AttachmentRepository attachmentRepository;


    @Transactional
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
                List<User> userList = new ArrayList<>();

                Attachment attachment = new Attachment();
                ClassPathResource imgFile = new ClassPathResource("/static/default.png");
                attachment.setFileName("user.png");
                attachment.setFileType("image/png");
                attachment.setContent(imgFile.getInputStream().readAllBytes());
                attachmentRepository.save(attachment);


                User user = new User();
                user.setId(null);
                user.setEmail("imomjonuzb@gmail.com");
                user.setPassword(passwordEncoder.encode("123"));
                user.setRoles(all);
                user.setAttachment(attachment);

                User user1 = new User();
                user1.setId(null);
                user1.setEmail("mirzabeknormakhmatov03@gmail.com");
                user1.setPassword(passwordEncoder.encode("123"));
                user1.setRoles(all);
                user1.setAttachment(attachment);

                User user2 = new User();
                user2.setId(null);
                user2.setEmail("dovudtoshxojaev@gmail.com");
                user2.setPassword(passwordEncoder.encode("123"));
                user2.setRoles(all);
                user2.setAttachment(attachment);

                userList.add(user);
                userList.add(user1);
                userList.add(user2);
                userRepository.saveAll(userList);


            }
        }


    }

}
