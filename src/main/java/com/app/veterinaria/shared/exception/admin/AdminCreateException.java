package com.app.veterinaria.shared.exception.admin;

public class AdminCreateException extends RuntimeException {

    public AdminCreateException(String message) {
        super(message);
    }

    public AdminCreateException(String message, Throwable cause) {
        super(message, cause);
    }
}
