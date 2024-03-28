package PCD.BACKEND.RECRAFTMARKET.service.auth;


import PCD.BACKEND.RECRAFTMARKET.dto.auth.AuthResponseDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.LoginDto;
import PCD.BACKEND.RECRAFTMARKET.dto.auth.RegisterDto;
import PCD.BACKEND.RECRAFTMARKET.dto.user.UserEntityDTOMapper;
import PCD.BACKEND.RECRAFTMARKET.model.role.Role;
import PCD.BACKEND.RECRAFTMARKET.model.token.Token;
import PCD.BACKEND.RECRAFTMARKET.model.token.TokenType;
import PCD.BACKEND.RECRAFTMARKET.model.user.UserEntity;
import PCD.BACKEND.RECRAFTMARKET.repository.RoleRepository;
import PCD.BACKEND.RECRAFTMARKET.repository.TokenRepository;
import PCD.BACKEND.RECRAFTMARKET.repository.UserEntityRepository;
import PCD.BACKEND.RECRAFTMARKET.security.jwt.JWTService;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import PCD.BACKEND.RECRAFTMARKET.exceptions.ResourceNotFoundException;

@AllArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserEntityRepository userEntityRepository;

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final UserEntityDTOMapper userEntityDTOMapper;

private RoleRepository roleRepository;
    public ResponseEntity<AuthResponseDto> register(@NotNull RegisterDto registerDto) {

        if (userEntityRepository.isUsernameRegistered(registerDto.getUsername())) {
            throw new IllegalArgumentException("Sorry, that username is already taken. Please choose a different one.");
        }

        Role role = roleRepository.fetchRoleByName("CLIENT")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found [CLIENT]."));

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(role);
        user.setAddress(registerDto.getAddress());
        user.setPhonenumber(registerDto.getPhonenumber());
        user.setPoints(0);

        UserEntity savedUser = userEntityRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        saveUserToken(savedUser, jwtToken);

        AuthResponseDto authResponseDto =
                AuthResponseDto.builder()
                        .userEntity(userEntityDTOMapper.apply(savedUser))
                        .token(jwtToken)
                        .build();
        return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);
    }

    public ResponseEntity<AuthResponseDto> login(@NotNull LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userEntityRepository.fetchUserWithUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("The email address specified was not found in our system."));

        String jwtToken = generateAndSaveToken(user);

        AuthResponseDto authResponseDto =
                AuthResponseDto.builder()
                        .userEntity(userEntityDTOMapper.apply(user))
                        .token(jwtToken)
                        .build();

        return ResponseEntity.ok(authResponseDto);
    }

    private String generateAndSaveToken(UserEntity user) {
        String jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return jwtToken;
    }

    private void saveUserToken(@NotNull UserEntity userEntity, @NotNull String jwtToken) {
        var token = Token.builder()
                .userEntity(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(@NotNull UserEntity userEntity) {
        var validUserTokens = tokenRepository.fetchAllValidTokenByUserId(userEntity.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
