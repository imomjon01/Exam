package uz.pdp.project.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterDto {
    @Email   @NotBlank private String  email;
    @Size(min = 6)     private String  password;
    private Integer    attachmentId;     // rasm optional
}


