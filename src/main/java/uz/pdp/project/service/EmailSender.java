package uz.pdp.project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String toEmail, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("mirzanormakhmatov@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Your email has been sent");
        message.setText( "Your password is: "+body);
        mailSender.send(message);
    }

    public String randomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }
}
