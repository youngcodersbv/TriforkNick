package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DishNotFoundException;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
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

    public List<Dish> findAllFilteredDishes(Category category, String diet) {
        List<Dish> dishes = new ArrayList<>();

        findAllDishes().stream()
                .filter(createFilter(category, diet))
                .forEach(dishes::add);

        return dishes;
    }

    private Predicate<Dish> createFilter(Category category, String diet) {
        return dish -> filter(category, diet, dish);
    }

    private boolean filter(Category category, String diet, Dish dish) {
        if(category == null && diet == null) {
            return true;
        }
        if(category != null) {
            if(!dish.hasType(category)) return false;
        }
        if(diet != null) {
            if(!dish.isOfDiet(diet)) return false;
        }
        return true;
    }
}
