package com.app.veterinaria.shared.exception.dueno;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class DuenoWithRelationsException extends BusinessException {

    public DuenoWithRelationsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
