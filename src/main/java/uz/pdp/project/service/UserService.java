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
    public void register(UserRegisterDto dto) {

        if (userRepo.existsByEmail(dto.getEmail()))
            throw new IllegalStateException("Bu e-mail allaqachon mavjud");

        Role userRole = roleRepo.findByRole(Roles.ADMIN)
                .orElseThrow(() -> new IllegalStateException("'ADMIN' roli topilmadi"));

        Attachment att = null;
        if (dto.getAttachmentId() != null)
            att = attRepo.findById(dto.getAttachmentId())
                    .orElseThrow(() -> new IllegalArgumentException("Attachment topilmadi"));

        String code = String.format("%06d", new Random().nextInt(1_000_000));

        User u = new User();
        u.setEmail(dto.getEmail());
        u.setPassword(encoder.encode(dto.getPassword()));
        u.setRoles(List.of(userRole));
        u.setAttachment(att);
        u.setVerificationCode(code);

        userRepo.save(u);

        // =====================  TODO: VERIFICATION  =====================
        // Shu yerda kodni e-mail / SMS yuborish servisiga uzating.
        // =================================================================
    }

    // >>> verify(...) metodini hamkoringiz shu klassga qo‘shadi
}


