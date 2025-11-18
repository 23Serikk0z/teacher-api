package kz.stech.teachback.auth.internal.service.impl;

import kz.stech.teachback.auth.internal.service.IAuthService;
import kz.stech.teachback.security.api.GenerateTokenFacade;
import kz.stech.teachback.shared.dto.UserDto;
import kz.stech.teachback.shared.dto.auth.AuthenticationRequestDto;
import kz.stech.teachback.shared.dto.auth.RegisterDto;
import kz.stech.teachback.shared.dto.auth.ResponseDto;
import kz.stech.teachback.user.api.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {


    private final UserFacade userFacade;
    private final GenerateTokenFacade generateTokenFacade;

    @Override
    public ResponseDto login(AuthenticationRequestDto request) {
        return userFacade.findUserByUsername(request.username())
                .map(user -> {
                    boolean passwordMatches = userFacade.checkPassword(request.username(), request.password());
                    if (!passwordMatches) {
                        throw new RuntimeException("Incorrect username or password");
                    }

                    String token = generateTokenFacade.generateToken(
                            user.getId(),
                            user.getUsername(),
                            user.getRoles());

                    String refreshToken = generateTokenFacade.generateRefreshToken(
                            user.getId(),
                            user.getUsername(),
                            user.getRoles());

                    return new ResponseDto(token, refreshToken);
                })
                .orElseThrow(() -> new RuntimeException("Incorrect username or password"));
    }

    @Override
    public ResponseDto refreshToken(String refreshToken) {
        if(!generateTokenFacade.validateToken(refreshToken))
            throw new  RuntimeException("Incorrect refresh token");


        UUID userId = generateTokenFacade.getUserIdFromToken(refreshToken);

        UserDto user = userFacade.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = generateTokenFacade.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRoles());

        String refreshTokenIssued = generateTokenFacade.generateRefreshToken(
                user.getId(),
                user.getUsername(),
                user.getRoles());

        return new ResponseDto(token, refreshTokenIssued);
    }

    @Override
    public ResponseDto register(RegisterDto request) {

        if(!request.password().equals(request.confirmPassword())) {
            throw new  RuntimeException("Incorrect confirm password");
        }

        userFacade.findUserByUsername(request.username())
                .ifPresent(user -> {
                    throw new RuntimeException("User already in use");
                });

        UserDto user = userFacade.createUser(
                request.username(),
                request.password(),
                request.firstName(),
                request.lastName(),
                request.type()
        );

        String token = generateTokenFacade.generateToken(
                user.getId(),
                user.getUsername(),
                user.getRoles());

        String refreshTokenIssued = generateTokenFacade.generateRefreshToken(
                user.getId(),
                user.getUsername(),
                user.getRoles());

        return new  ResponseDto(token, refreshTokenIssued);
    }
}
