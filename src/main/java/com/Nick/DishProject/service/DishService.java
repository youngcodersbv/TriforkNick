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

    private final DishRepository dietRepository;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dietRepository =dishRepo;
    }


    public Dish findDishById(Long id) {
        return dietRepository.findDishById(id).orElseThrow(() -> new DishNotFoundException("Dish by id "+id+" was not found."));
    }

    public Dish addDish(Dish dish) {
        return dietRepository.save(dish);
    }

    public Dish updateDish(Dish dish) {
        return dietRepository.save(dish);
    }

    public void deleteDishById(Long id) {
        dietRepository.deleteDishById(id);
    }

    public List<Dish> findAllDishes() {
        return StreamSupport
                .stream(dietRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

}
