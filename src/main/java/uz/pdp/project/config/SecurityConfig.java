package uz.pdp.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> {
            registry
                    .requestMatchers("login", "/").permitAll()
                    .requestMatchers("register", "/").permitAll()
                    .requestMatchers("/api/auth/register").permitAll()
                    .requestMatchers("/task/**").permitAll()
                    .anyRequest().authenticated();
        });

        http.formLogin(formLogin -> {
            /*            formLogin.defaultSuccessUrl("#", true).permitAll();*/

        });

        http.logout(logout ->
                logout.logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
        );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
    }
}
