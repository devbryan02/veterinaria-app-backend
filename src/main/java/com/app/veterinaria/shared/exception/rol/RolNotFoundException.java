package com.app.veterinaria.shared.exception.rol;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class RolNotFoundException extends BusinessException {

    public RolNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
