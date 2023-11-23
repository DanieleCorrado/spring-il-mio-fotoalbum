package com.example.backend.exceptions;

public class PhotoNotFoundException extends RuntimeException{

    public PhotoNotFoundException(String message) {
        super(message);
    }
}
