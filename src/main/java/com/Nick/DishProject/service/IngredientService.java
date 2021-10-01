package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.IngredientNotFoundException;
import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository repo) {
        this.ingredientRepository = repo;
    }

    public Ingredient findIngredientById(Long id) {
        return ingredientRepository.findIngredientById(id).orElseThrow(() -> new IngredientNotFoundException("Ingredient by id "+id+" was not found."));
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredientById(Long id) {
        ingredientRepository.deleteIngredientById(id);
    }

    public List<Ingredient> findAllIngredients() {
        return StreamSupport
                .stream(ingredientRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}
