package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {

    private IngredientService ingredientService;

    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.findAllIngredients();
        return new ResponseEntity<>(ingredients, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Ingredient> addDiet(@Valid @RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientService.addIngredient(ingredient);
        return new ResponseEntity<>(newIngredient,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Ingredient> updateIngredient(@Valid @RequestBody Ingredient ingredient) {
        Ingredient newIngredient = ingredientService.updateIngredient(ingredient);
        return new ResponseEntity<>(newIngredient, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteIngredientById(@PathVariable("id")Long id) {
        ingredientService.deleteIngredientById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
