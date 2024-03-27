package PCD.BACKEND.RECRAFTMARKET.dto.auth;

import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
//user entityDTO and token
public class AuthResponseDto {
    private UserEntityDTO userEntity;
    private String token;


}
