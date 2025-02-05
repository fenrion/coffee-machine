package com.aisa.test.domain.exception;

public class NotEnoughIngredientsException extends RuntimeException{
    public NotEnoughIngredientsException(String message) {
        super(message);
    }
}
