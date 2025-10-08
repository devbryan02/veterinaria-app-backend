package com.app.veterinaria.shared.exception.dueno;

public class DuenoCreateException extends RuntimeException
{
    public DuenoCreateException(String message) {
        super(message);
    }

    public DuenoCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
