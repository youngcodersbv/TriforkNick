package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.model.DishIngredient;
import com.Nick.DishProject.model.DishIngredientId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DishIngredientRepository extends CrudRepository<DishIngredient, DishIngredientId> {
    public List<DishIngredient> findDishIngredientsByDish(Dish dish);
}
