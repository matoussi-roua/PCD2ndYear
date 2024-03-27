package PCD.BACKEND.RECRAFTMARKET.security.jwt;

import PCD.BACKEND.RECRAFTMARKET.exceptions.ExpiredTokenException;
import PCD.BACKEND.RECRAFTMARKET.exceptions.InvalidTokenException;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;
import PCD.BACKEND.RECRAFTMARKET.exceptions.RevokedTokenException;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.TokenRepository;
import PCD.BACKEND.RECRAFTMARKET.security.utility.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTService jwtService;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request,response);
            return;
        }


        final String jwtToken = authHeader.substring(7);

        if(!jwtService.validateToken(jwtToken))  {
            filterChain.doFilter(request,response) ;
            return;
        }

        String username = jwtService.extractUsernameFromJwt(jwtToken);

        if(username == null || SecurityContextHolder.getContext().getAuthentication() != null )  {
            filterChain.doFilter(request,response);
            return;
        }

        UserEntity userEntity = (UserEntity) this.customUserDetailsService.loadUserByUsername(username);

        var isTokenValid = tokenRepository.findByToken(jwtToken).map(t -> !t.isExpired() && !t.isRevoked()).orElse(false);
        var tokenSaved = tokenRepository.findByToken(jwtToken).orElse(null);
        if(!isTokenValid)
        {
            throw new ResourceNotFoundException("Token not found.");
        }

        if(jwtService.isTokenExpired(jwtToken))
        {
            throw new ExpiredTokenException("Token has expired.");
        }
        if(tokenSaved.isRevoked())
        {
            throw new RevokedTokenException("Token has been revoked");
        }
        if(!jwtService.isTokenValid(jwtToken, userEntity))
        {
            throw new InvalidTokenException("Invalid token");
        }


        if(!jwtService.isTokenValid(jwtToken, userEntity) || !isTokenValid )  {filterChain.doFilter(request,response);return;}

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userEntity,null,userEntity.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request,response);
    }
}
