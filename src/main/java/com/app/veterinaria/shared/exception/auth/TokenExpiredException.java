package com.app.veterinaria.shared.exception.auth;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BusinessException {

    public TokenExpiredException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

}
