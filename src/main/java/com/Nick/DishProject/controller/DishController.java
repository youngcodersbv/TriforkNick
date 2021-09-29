package com.Nick.DishProject.controller;


import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService=dishService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Dish>> getAllDishes() {
        List<Dish> dishes = dishService.findAllDishes();
        return new ResponseEntity<>(dishes, HttpStatus.OK);
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