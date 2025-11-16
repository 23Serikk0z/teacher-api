package kz.stech.teachback.user.internal.service.impl;

import kz.stech.teachback.security.api.SecurityFacade;
import kz.stech.teachback.security.internal.service.CurrentUserService;
import kz.stech.teachback.shared.dto.UserDto;
import kz.stech.teachback.user.internal.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final SecurityFacade securityFacade;
    private final CurrentUserService currentUserService;

    @Override
    public UserDto getCurrentUser() {
        return securityFacade.getCurrentUser();
    }
}
