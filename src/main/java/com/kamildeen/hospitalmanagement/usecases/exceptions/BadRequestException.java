package com.kamildeen.hospitalmanagement.usecases.exceptions;


public class BadRequestException extends RuntimeException {
    public BadRequestException(String message){
        super(message);
    }
}
