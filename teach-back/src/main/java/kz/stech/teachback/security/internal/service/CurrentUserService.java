package kz.stech.teachback.security.internal.service;

import kz.stech.teachback.security.api.SecurityFacade;
import kz.stech.teachback.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CurrentUserService implements SecurityFacade {

    @Override
    public UUID getCurrentUserId() {
        String sub = getClaim("sub");
        if (sub == null) {
            throw new RuntimeException("User ID (sub) not found in JWT");
        }
        return UUID.fromString(sub);
    }

    @Override
    public Set<String> getRoles() {
        Authentication authentication = getAuthentication();
        if (authentication == null) return Set.of();

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .collect(Collectors.toSet());
    }

    @Override
    public String getCurrentUserName() {
        return getClaim("username", getClaim("sub", "unknown"));
    }

    @Override
    public UserDto getCurrentUser() {
        return new UserDto(
                getCurrentUserId(),
                getCurrentUserName(),
                getRoles()
        );
    }


    private Authentication getAuthentication() {
        return org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    private String getClaim(String claim) {
        return getClaim(claim, null);
    }

    private String getClaim(String claim, String fallback) {
        Authentication authentication = getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            Object value = jwtAuth.getToken().getClaim(claim);
            return value != null ? value.toString() : fallback;
        }
        return fallback;
    }
}
