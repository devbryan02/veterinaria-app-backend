package com.app.veterinaria.shared.exception.mascota;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MascotaNotFoundException extends BusinessException {

    public MascotaNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }
}
