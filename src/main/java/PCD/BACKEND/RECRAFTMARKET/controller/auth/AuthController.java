package PCD.BACKEND.RECRAFTMARKET.controller.auth;

import PCD.BACKEND.RECRAFTMARKET.dto.auth.AuthResponseDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.LoginDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.RegisterDto;
import PCD.BACKEND.RECRAFTMARKET.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
//this method of authentication
@GetMapping("/logout1")
public String logout (@AuthenticationPrincipal UserDetails userDetails){

    return authService.logout(userDetails);
}

        // Additional logic in your controller method, if needed
    }
//The AuthService performs actions like checking credentials,
//generating tokens, and handling authentication.
