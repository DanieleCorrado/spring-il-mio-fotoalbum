package com.example.backend.exceptions;

public class CategoryNameUniqueException extends  RuntimeException{

    public CategoryNameUniqueException(String message) {
        super(message);
    }
}
