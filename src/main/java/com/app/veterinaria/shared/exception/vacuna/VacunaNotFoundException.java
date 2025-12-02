package com.app.veterinaria.shared.exception.vacuna;

import com.app.veterinaria.shared.exception.BusinessException;
import org.springframework.http.HttpStatus;

public class VacunaNotFoundException extends BusinessException {

    public VacunaNotFoundException(String message){
        super(message, HttpStatus.NOT_FOUND);
    }

}
