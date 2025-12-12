package com.proiect.subtrack.utils.errors;

public class SubscriptionNotFoundError extends RuntimeException{
    public SubscriptionNotFoundError(String message){
        super(message);
    }
}
