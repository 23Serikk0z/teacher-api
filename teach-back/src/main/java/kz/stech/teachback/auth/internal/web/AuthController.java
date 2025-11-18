package kz.stech.teachback.auth.internal.web;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kz.stech.teachback.auth.internal.service.IAuthService;
import kz.stech.teachback.shared.dto.auth.AuthenticationRequestDto;
import kz.stech.teachback.shared.dto.auth.RegisterDto;
import kz.stech.teachback.shared.dto.auth.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@RequestBody @Valid RegisterDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@RequestBody @Valid AuthenticationRequestDto request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.login(request));
    }


    @PostMapping("/refreshToken")
    public ResponseEntity<ResponseDto> refreshToken(@RequestParam @NotNull @NotBlank String refreshToken) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(authService.refreshToken(refreshToken));
    }
}
