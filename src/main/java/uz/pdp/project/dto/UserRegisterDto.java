package uz.pdp.project.dto;


import lombok.Data;

@Data
public class UserRegisterDto {
    private String email;
    private String password;
    String randomCode;

    public UserRegisterDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}


