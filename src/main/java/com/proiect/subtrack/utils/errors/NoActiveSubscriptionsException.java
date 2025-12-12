package com.proiect.subtrack.utils.errors;

public class NoActiveSubscriptionsException extends RuntimeException{

    public NoActiveSubscriptionsException(String message){
        super(message);
    }
}
