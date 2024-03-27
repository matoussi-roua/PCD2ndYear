package PCD.BACKEND.RECRAFTMARKET.controller.auth;

import PCD.BACKEND.RECRAFTMARKET.dto.auth.AuthResponseDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.LoginDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.RegisterDto;
import PCD.BACKEND.RECRAFTMARKET.service.auth.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//handel http request for login and register
@RestController
@RequestMapping("api/v1/auth")
public class AuthController {


    private final AuthService authService;


    public AuthController (AuthService authService)
    {
        this.authService = authService;
    }

//It calls the register method of the AuthService to actually register the user
// and returns an AuthResponseDto as response
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterDto registerDto)
    {
        return authService.register(registerDto);
    }
//Calls the login method of the AuthService and Returns an AuthResponseDto with relevant information.
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto)
    {
        return authService.login(loginDto);
    }
//The AuthService performs actions like checking credentials,
//generating tokens, and handling authentication.
}