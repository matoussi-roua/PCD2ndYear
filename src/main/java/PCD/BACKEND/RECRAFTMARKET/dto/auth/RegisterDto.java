package PCD.BACKEND.RECRAFTMARKET.dto.auth;
import jakarta.validation.constraints.Pattern;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
//not finished
//username password...
public class RegisterDto {

    private String username;
    private String firstname;
    private String lastname;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
            message = "Invalid password. Passwords must be at least 8 characters long and include at least one uppercase letter, " +
                    "one lowercase letter, one digit, and one special character.")
    private String password;
    
    private String phonenumber;


    private String address;



}


