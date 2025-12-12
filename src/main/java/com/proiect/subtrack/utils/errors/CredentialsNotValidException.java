package com.proiect.subtrack.utils.errors;

/**
 * Exception thrown when credentials (email, password, etc.) are not valid
 */
public class CredentialsNotValidException extends RuntimeException {

    public CredentialsNotValidException(String message) {
        super(message);
    }

    public CredentialsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}

