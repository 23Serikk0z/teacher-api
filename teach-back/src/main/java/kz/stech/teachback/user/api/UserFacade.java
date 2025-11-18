package kz.stech.teachback.user.api;

import kz.stech.teachback.shared.dto.UserDto;
import kz.stech.teachback.shared.dto.auth.ERegisterRoleType;

import java.util.Optional;
import java.util.UUID;

public interface UserFacade {
    Optional<UserDto> findUserByUsername(String username);
    Optional<UserDto> findUserById(UUID userId);
    boolean checkPassword(String username, String password);

    UserDto createUser(String username, String password, String firstName, String lastName, ERegisterRoleType role);
}
