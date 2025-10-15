package com.app.veterinaria.shared.exception.dueno;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuenoNotFoundException extends BusinessException {

    public DuenoNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
