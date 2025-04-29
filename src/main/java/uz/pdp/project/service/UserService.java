package uz.pdp.project.service;
// service/UserService.java

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.project.entity.*;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.repo.*;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository       userRepo;
    private final RoleRepository       roleRepo;
    private final PasswordEncoder      encoder;

    public String register(UserRegisterDto dto) {

        // 1) E-mail takrorlanmasin
//        if (userRepo.findByEmail(dto.getEmail()))
//            throw new IllegalStateException("Bu e-mail allaqachon mavjud");

        Role userRole = roleRepo.findByRole(Roles.ADMIN)          // Roles.USER desangiz ham bo‘ladi
                .orElseThrow(() -> new IllegalStateException("'ADMIN' roli topilmadi"));



        String code = String.format("%06d", new Random().nextInt(1_000_000));

        // 5) User ni saqlaymiz
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setRoles(List.of(userRole));
        u.setVerificationCode(code);

        userRepo.save(u);

        return code;
    }


}


