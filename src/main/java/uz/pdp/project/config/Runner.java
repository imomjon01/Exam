package uz.pdp.project.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.project.service.UserService;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

    public final UserService userService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {

    }
}
