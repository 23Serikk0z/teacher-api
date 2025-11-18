package kz.stech.teachback.security.internal.service;

import kz.stech.teachback.security.api.GenerateTokenFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateTokenService implements GenerateTokenFacade {

    private final AuthService authService;

    @Override
    public String generateToken(UUID userId, String username, Set<String> roles) {
        return authService.generateToken(userId, username, roles);
    }

    @Override
    public String generateRefreshToken(UUID userId, String username, Set<String> roles) {
        return authService.generateRefreshToken(userId, username, roles);
    }

    @Override
    public boolean validateToken(String token) {
        return authService.validateToken(token);
    }

    @Override
    public UUID getUserIdFromToken(String token) {
        return Optional.ofNullable(authService.getUserIdFromToken(token))
                .orElseThrow(
                        () -> new RuntimeException("Invalid token provided")
                );
    }
}
