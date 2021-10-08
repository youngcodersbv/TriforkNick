package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.model.Dish;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface DishRepository  extends CrudRepository<Dish,Long> {

    Optional<Dish> findDishById(long id);
    void deleteDishById(Long id);
}
