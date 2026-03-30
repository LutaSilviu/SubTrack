package com.proiect.subtrack.utils.errors;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message){
        super(message);
    }
}
