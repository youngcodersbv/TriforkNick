package com.Nick.DishProject.exception;

public class DishNotFoundException extends RuntimeException{
    public DishNotFoundException(String message) {
        super(message);
    }
}
