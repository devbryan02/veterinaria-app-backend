package com.app.veterinaria.shared.exception.dueno;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuenoCreateException extends BusinessException {

    public DuenoCreateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
