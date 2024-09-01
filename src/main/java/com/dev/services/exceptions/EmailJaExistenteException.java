package com.dev.services.exceptions;

public class EmailJaExistenteException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public EmailJaExistenteException(String message) {
        super(message);
    }

    public EmailJaExistenteException(String message, Throwable cause) {
        super(message, cause);
    }
}