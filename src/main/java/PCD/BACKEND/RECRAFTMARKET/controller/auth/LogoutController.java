package PCD.BACKEND.RECRAFTMARKET.controller.auth;


import PCD.BACKEND.RECRAFTMARKET.service.auth.LogoutService;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class LogoutController {
    private final LogoutService logoutService;


    public LogoutController (LogoutService logoutService)
    {
        this.logoutService = logoutService;
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // Call the logout method from the LogoutService
        logoutService.logout(request, response, authentication);
    }

}
