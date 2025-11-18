package kz.stech.teachback.user.internal.service.impl;


import kz.stech.teachback.shared.dto.UserDto;
import kz.stech.teachback.shared.dto.auth.ERegisterRoleType;
import kz.stech.teachback.shared.dto.event.TeacherAccountCreatedEvent;
import kz.stech.teachback.user.api.UserFacade;
import kz.stech.teachback.user.internal.model.Role;
import kz.stech.teachback.user.internal.model.User;
import kz.stech.teachback.user.internal.repository.RoleRepository;
import kz.stech.teachback.user.internal.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ApplicationEventPublisher publisher;

    @Override
    public Optional<UserDto> findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().stream().map(Role::getCode).collect(Collectors.toSet())
                ));
    }

    @Override
    public Optional<UserDto> findUserById(UUID userId) {
        return userRepository.findById(userId)
                .map(user -> new UserDto(
                        user.getId(),
                        user.getUsername(),
                        user.getRole().stream().map(Role::getCode).collect(Collectors.toSet())
                ));

    }

    @Override
    public boolean checkPassword(String username, String password) {
        try {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new Exception("Username not found"));

            return passwordEncoder.matches(password, user.getPassword());

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional
    public UserDto createUser(String username, String password, String firstName, String lastName, ERegisterRoleType role) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);

        Role roleEntity = roleRepository.findByCode(role.name()).orElseThrow(
                () -> new RuntimeException("Role not found")
        );

        user.setRole(Set.of(roleEntity));

        userRepository.save(user);

        if(roleEntity.getCode().equals(ERegisterRoleType.ROLE_TEACHER.name())) {
            publisher.publishEvent(new TeacherAccountCreatedEvent(user.getId()));
        }

        return new UserDto(user.getId(), user.getUsername(), user.getRole().stream().map(Role::getCode).collect(Collectors.toSet()));
    }
}
