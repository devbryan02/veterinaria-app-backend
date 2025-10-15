package com.app.veterinaria.shared.exception.vacuna;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class VacunaCreateException extends BusinessException {

    public VacunaCreateException(String message){
        super(message, HttpStatus.BAD_REQUEST);
    }
}
