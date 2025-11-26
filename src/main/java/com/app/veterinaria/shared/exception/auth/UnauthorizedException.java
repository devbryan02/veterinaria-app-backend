package com.app.veterinaria.shared.exception.auth;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BusinessException {

    public UnauthorizedException(String message){
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
