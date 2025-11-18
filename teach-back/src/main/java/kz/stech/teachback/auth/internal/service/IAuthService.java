package kz.stech.teachback.auth.internal.service;

import kz.stech.teachback.shared.dto.auth.AuthenticationRequestDto;
import kz.stech.teachback.shared.dto.auth.RegisterDto;
import kz.stech.teachback.shared.dto.auth.ResponseDto;

public interface IAuthService {
    ResponseDto login(AuthenticationRequestDto request);
    ResponseDto refreshToken(String refreshToken);

    ResponseDto register(RegisterDto request);
}
