package kz.stech.teachback.shared.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(

        @NotBlank
        @NotNull
        String username,
        @NotBlank
        @NotNull
        String password,

        @NotBlank
        @NotNull
        String confirmPassword,

        @NotBlank
        @NotNull
        String firstName,

        @NotBlank
        @NotNull
        String lastName,

        @NotNull
        ERegisterRoleType type) { }
