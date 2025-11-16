package kz.stech.teachback.security.api;

import kz.stech.teachback.shared.dto.UserDto;

import java.util.Set;
import java.util.UUID;

public interface SecurityFacade {

    UUID getCurrentUserId();
    Set<String> getRoles();
    String getCurrentUserName();
    UserDto getCurrentUser();
}
