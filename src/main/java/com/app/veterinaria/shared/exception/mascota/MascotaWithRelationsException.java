package com.app.veterinaria.shared.exception.mascota;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class MascotaWithRelationsException extends BusinessException {

    public MascotaWithRelationsException(String message){
        super(message, HttpStatus.CONFLICT);
    }
}
