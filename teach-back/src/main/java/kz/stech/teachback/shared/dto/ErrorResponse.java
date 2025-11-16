package kz.stech.teachback.shared.dto;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) { }
