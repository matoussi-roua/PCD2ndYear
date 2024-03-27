package PCD.BACKEND.RECRAFTMARKET.dto.auth;

import jakarta.validation.constraints.NotBlank;

//username and password
public class LoginDto {

    private String username;
    @NotBlank
    private String password;


    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

}
