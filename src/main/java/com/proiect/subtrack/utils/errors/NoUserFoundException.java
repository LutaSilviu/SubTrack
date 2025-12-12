package com.proiect.subtrack.utils.errors;

public class NoUserFoundException extends RuntimeException{
    public NoUserFoundException(String message){
        super(message);
    }
}
