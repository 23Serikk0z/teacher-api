package kz.stech.teachback.security.api;

import java.util.Set;
import java.util.UUID;

public interface GenerateTokenFacade {
    String generateToken(UUID userId, String username, Set<String> roles);
    String generateRefreshToken(UUID userId, String username, Set<String> roles);

    boolean validateToken(String token);
    UUID getUserIdFromToken(String token);
}
