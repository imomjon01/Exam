package uz.pdp.project.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.project.dto.UserRegisterDto;
import uz.pdp.project.service.UserService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterDto dto) {
        userService.register(dto);
        return ResponseEntity.ok("Kod yuborildi – verify endpointini hamkoringiz bajaradi.");
    }
}

