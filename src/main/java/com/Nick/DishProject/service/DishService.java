package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DishNotFoundException;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DishService {

    private final DishRepository dishRepo;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dishRepo=dishRepo;
    }

    public Dish findDishById(Long id) {
        return dishRepo.findDishById(id).orElseThrow(() -> new DishNotFoundException("User by id "+id+" was not found."));
    }

    public Dish addDish(Dish dish) {
        return dishRepo.save(dish);
    }

    public Dish updateDish(Dish dish) {
        return dishRepo.save(dish);
    }

    public void deleteDishById(Long id) {
        dishRepo.deleteDishById(id);
    }

    public List<Dish> findAllDishes() {
        return StreamSupport
                .stream(dishRepo.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}
