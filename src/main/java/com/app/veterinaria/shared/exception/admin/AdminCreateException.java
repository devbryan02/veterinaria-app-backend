package com.app.veterinaria.shared.exception.admin;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class AdminCreateException extends BusinessException {

    public AdminCreateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
