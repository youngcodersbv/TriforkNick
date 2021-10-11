package com.Nick.DishProject.controller;


import com.Nick.DishProject.dto.DishDto;
import com.Nick.DishProject.model.*;
import com.Nick.DishProject.service.DietService;
import com.Nick.DishProject.service.DishIngredientService;
import com.Nick.DishProject.service.DishService;
import com.Nick.DishProject.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final DietService dietService;
    private final DishIngredientService dishIngredientService;
    private final IngredientService ingredientService;

    public DishController(DishService dishService, DietService dietService,
                          DishIngredientService dishIngredientService,
                          IngredientService ingredientService) {
        this.dishService=dishService;
        this.dietService=dietService;
        this.dishIngredientService = dishIngredientService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DishDto>> getAllDishes(@RequestParam(value = "category",required = false) List<String> category,
                                                   @RequestParam(value = "diet",required = false) List<String> diet) {
        List<String> categoryToUpper = new ArrayList<>();
        if(category != null) {
            for (String s : category) {
                categoryToUpper.add(s.toUpperCase(Locale.ROOT));
            }
        }
        List<String> dietToUpper = new ArrayList<>();
        if(diet != null) {
            for (String s : diet) {
                dietToUpper.add(s.toUpperCase(Locale.ROOT));
            }
        }
        List<Dish> dishes = dishService.findAllFilteredDishes(categoryToUpper,dietToUpper);

        List<DishDto> dto = new ArrayList<>();

        if(dishes != null && !dishes.isEmpty()) {
            for(Dish d : dishes) {
                dto.add(new DishDto(d));
            }
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
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

    @PutMapping("/updatedto")
    @Transactional
    public ResponseEntity<Dish> updateDishDto(@RequestBody DishDto dishDto) {

        Dish dish = dishDto.createDish();
        List<DishIngredient> dishIngredientList = dishIngredientService.findDishIngredientsByDish(dish);
        dishIngredientList.stream().forEach(dI -> dishIngredientService.deleteDishIngredientById(dI.getId()));


        dishService.updateDish(dish);
        Set<Ingredient> ingredient = dishDto.getIngredients();

        System.out.println(dish.toString());


        if(ingredient != null) {
            for (Ingredient i : ingredient) {
                DishIngredient dI = new DishIngredient();
                dI.setIngredient(i);
                dI.setDish(dish);

                dish.getIngredients().add(dI);
                i.getDishes().add(dI);

                //dI.setAmountNeeded(dishDto.getAmountNeeded());
                dishIngredientService.updateDishIngredient(dI);
                ingredientService.updateIngredient(i);
            }
        }


        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @PostMapping("/adddto")
    @Transactional
    public ResponseEntity<Dish> addDishDto(@RequestBody DishDto dishDto) {

        System.out.println(dishDto.getAmount());

        Dish dish = dishDto.createDish();
        Set<Ingredient> ingredient = dishDto.getIngredients();

        dishService.addDish(dish);
        System.out.println(dish.toString());

        if(ingredient != null) {
            for (Ingredient i : ingredient) {

                DishIngredient dI = new DishIngredient();
                dI.setIngredient(i);
                dI.setDish(dish);

                dish.getIngredients().add(dI);
                i.getDishes().add(dI);

                //dI.setAmountNeeded(dishDto.getAmountNeeded());

                dishIngredientService.addDishIngredient(dI);
                ingredientService.addIngredient(i);
            }
        }


        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteDishById(@PathVariable("id")Long id) {
        dishService.deleteDishById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}