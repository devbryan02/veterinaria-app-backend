package com.app.veterinaria.shared.exception.mascota;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MascotaCreateException extends BusinessException {

    public MascotaCreateException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
