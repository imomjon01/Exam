package uz.pdp.project.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class Cabinet {

    @GetMapping("/")
    public String cabinet(){
        return "cabinet";
    }
}
