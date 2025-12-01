package com.app.veterinaria.shared.exception.user;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class UsuarioNotFoundException extends BusinessException {

    public UsuarioNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
