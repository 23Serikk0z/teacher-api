package kz.stech.teachback.security.internal.service;

import kz.stech.teachback.security.api.SecurityFacade;
import kz.stech.teachback.shared.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
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
        return UUID.fromString(getClaim("sub"));
    }

    @Override
    public Set<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return Set.of();
        }

        return authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(r -> r.startsWith("ROLE_"))
                .collect(Collectors.toSet());
    }

    @Override
    public String getCurrentUserName() {
        return getClaim("preferred_username", getClaim("sub"));
    }

    @Override
    public UserDto getCurrentUser() {

        return new UserDto(
                this.getCurrentUserId(),
                this.getCurrentUserName(),
                this.getRoles(),
                this.getClaim("given_name"),
                this.getClaim("family_name")
        );
    }

    private String getClaim(String claim) {
        return getClaim(claim, null);
    }

    private String getClaim(String claim, String fallback) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return (String) jwtAuthenticationToken.getToken().getClaim(claim);
        }
        return fallback;
    }
}
