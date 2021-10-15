package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DishIngredientNotFoundException;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.model.DishIngredient;
import com.Nick.DishProject.model.DishIngredientId;
import com.Nick.DishProject.repository.DishIngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DishIngredientService {

    DishIngredientRepository dishIngredientRepository;

    @Autowired
    public DishIngredientService(DishIngredientRepository dishIngredientRepository) {
        this.dishIngredientRepository = dishIngredientRepository;
    }

    public DishIngredient findDishIngredientById(DishIngredientId id) {
        return dishIngredientRepository.findById(id).orElseThrow(() -> new DishIngredientNotFoundException("DishIng by id "+id+" was not found."));
    }

    public DishIngredient addDishIngredient(DishIngredient dishIngredient) {
        return dishIngredientRepository.save(dishIngredient);
    }

    public List<DishIngredient> findDishIngredientsByDish(Dish dish) {
        return dishIngredientRepository.findDishIngredientsByDish(dish);
    }

    public DishIngredient updateDishIngredient(DishIngredient dishIngredient) {
        return dishIngredientRepository.save(dishIngredient);
    }

    public void deleteDishIngredientById(DishIngredientId id) {
        dishIngredientRepository.deleteById(id);
    }

    public List<DishIngredient> findAllDishIngredients() {
        return StreamSupport
                .stream(dishIngredientRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}