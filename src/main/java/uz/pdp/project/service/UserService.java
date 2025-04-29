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
    private final AttachmentRepository attRepo;
    private final PasswordEncoder      encoder;

    /** Foydalanuvchini ro‘yxatdan o‘tkazadi va 6 xonali verificationCode yaratadi */
    /**
     * Foydalanuvchini ro‘yxatdan o‘tkazadi va
     * generatsiya qilingan 6 xonali verificationCode ni qaytaradi.
     */
    public String register(UserRegisterDto dto) {

        // 1) E-mail takrorlanmasin
        if (userRepo.existsByEmail(dto.getEmail()))
            throw new IllegalStateException("Bu e-mail allaqachon mavjud");

        // 2) Roli
        Role userRole = roleRepo.findByRole(Roles.ADMIN)          // Roles.USER desangiz ham bo‘ladi
                .orElseThrow(() -> new IllegalStateException("'ADMIN' roli topilmadi"));

        // 3) Attachment — ixtiyoriy
        Attachment att = null;
        if (dto.getAttachmentId() != null) {
            att = attRepo.findById(dto.getAttachmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Attachment topilmadi"));
        }

        // 4) 6 xonali tasdiqlash kodi
        String code = String.format("%06d", new Random().nextInt(1_000_000));

        // 5) User ni saqlaymiz
        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setRoles(List.of(userRole));
        u.setAttachment(att);
        u.setVerificationCode(code);

        userRepo.save(u);

        // 6) (ixtiyoriy) kodni yuborish servisi
        // mailService.sendVerification(u.getEmail(), code);

        // 7) Controller sessiyaga qo‘yishi uchun kodni qaytaramiz
        return code;
    }


    // >>> verify(...) metodini hamkoringiz shu klassga qo‘shadi
}


