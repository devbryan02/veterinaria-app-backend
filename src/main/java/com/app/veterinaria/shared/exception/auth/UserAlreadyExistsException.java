package com.app.veterinaria.shared.exception.auth;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends BusinessException {

    public UserAlreadyExistsException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
