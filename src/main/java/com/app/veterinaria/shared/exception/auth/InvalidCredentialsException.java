package com.app.veterinaria.shared.exception.auth;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException {

    public InvalidCredentialsException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }

}