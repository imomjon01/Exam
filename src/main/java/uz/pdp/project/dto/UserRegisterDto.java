package uz.pdp.project.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
}


