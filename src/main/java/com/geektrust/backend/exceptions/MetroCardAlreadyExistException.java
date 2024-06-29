package com.geektrust.backend.exceptions;

public class MetroCardAlreadyExistException extends RuntimeException {

    public MetroCardAlreadyExistException(String msg){
        super(msg);
    }
}
