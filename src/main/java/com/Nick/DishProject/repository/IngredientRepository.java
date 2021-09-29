package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient,Long> {

    Ingredient findById(long id);

}
