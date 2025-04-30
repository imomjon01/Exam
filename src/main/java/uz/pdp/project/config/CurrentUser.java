package uz.pdp.project.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uz.pdp.project.entity.User;
import uz.pdp.project.repo.UserRepository;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class CurrentUser implements UserDetailsService {
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }
}
