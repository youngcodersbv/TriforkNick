package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DishNotFoundException;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepo) {
        this.dishRepository =dishRepo;
    }

    public Dish findDishById(Long id) {
        return dishRepository.findDishById(id).orElseThrow(() -> new DishNotFoundException("Dish by id "+id+" was not found."));
    }

    public Dish addDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public Dish updateDish(Dish dish) {
        return dishRepository.save(dish);
    }

    public void deleteDishById(Long id) {
        dishRepository.deleteDishById(id);
    }

    public List<Dish> findAllDishes() {
        return StreamSupport
                .stream(dishRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }

    public List<Dish> findAllFilteredDishes(List<String> categories, List<String> diets) {
        if(categories == null) categories = new ArrayList<>();
        if(diets == null) diets = new ArrayList<>();

        List<Dish> dishes = new ArrayList<>();
        List<Dish> allDishes = findAllDishes();
        allDishes.stream()
                .filter(createFilter(categories, diets))
                .forEach(dishes::add);

        return dishes;
    }

    private Predicate<Dish> createFilter(List<String> categories, List<String> diets) {
        return dish -> filter(categories, diets, dish);
    }

    private boolean filter(List<String> categories, List<String> diets, Dish dish) {
        if(categories == null && diets == null) {
            return true;
        } else if(categories.isEmpty() && diets.isEmpty()) {
            return true;
        }
        if(!categories.isEmpty()) {
            if (dish.getCategories().stream()
                    .filter(cat -> categories.contains(cat.getType().toUpperCase(Locale.ROOT)))
                    .collect(Collectors.toList()).isEmpty()) return false;
;        }
        if(!diets.isEmpty()) {
            if(dish.getDiets().stream()
                    .filter(diet -> diets.contains(diet.getType().toUpperCase(Locale.ROOT)))
                    .collect(Collectors.toList()).isEmpty()) return false;
        }
        return true;
    }
}
