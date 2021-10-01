package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.service.IngredientService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

}
