package com.Nick.DishProject.controller;


import com.Nick.DishProject.dto.DishDTO;
import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.service.DietService;
import com.Nick.DishProject.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final DietService dietService;

    public DishController(DishService dishService, DietService dietService) {
        this.dishService=dishService;
        this.dietService=dietService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getAllDishes(@RequestParam(value = "type",required = false) Dish.DishType type,
                                                   @RequestParam(value = "diet",required = false) Diet.DietType diet) {
        List<Dish> dishes = dishService.findAllDishes();
        List<Dish> result = new ArrayList<>();

        dishes.stream()
                .filter(dish -> {
                    if(type != null) {
                        if(dish.getType() == null) {
                            return false;
                        }
                        if(dish.getType().equals(type)) return true;
                        else return false;
                    }
                    return true;
                }).forEach(result::add);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Dish> getDishById(@PathVariable("id") Long id) {
        Dish dish = dishService.findDishById(id);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PostMapping("/add")
    @Transactional
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish) {

        Dish newDish = dishService.addDish(dish);
        return new ResponseEntity<Dish>(newDish, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Dish> updateDish(@RequestBody Dish dish) {
        Dish newDish = dishService.updateDish(dish);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteDishById(@PathVariable("id")Long id) {
        dishService.deleteDishById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}