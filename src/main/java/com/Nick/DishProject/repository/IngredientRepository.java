package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {

    Optional<Ingredient> findIngredientById(long id);
    void deleteIngredientById(Long id);
}
