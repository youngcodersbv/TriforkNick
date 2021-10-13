package com.Nick.DishProject.exception;

public class DishIngredientNotFoundException extends RuntimeException{
    public DishIngredientNotFoundException(String e) {
        super(e);
    }
}
