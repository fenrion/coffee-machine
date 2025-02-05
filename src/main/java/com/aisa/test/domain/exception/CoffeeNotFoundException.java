package com.aisa.test.domain.exception;

public class CoffeeNotFoundException extends RuntimeException{
    public CoffeeNotFoundException(String message) {
        super(message);
    }
}
