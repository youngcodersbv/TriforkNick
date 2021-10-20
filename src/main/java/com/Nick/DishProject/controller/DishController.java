package com.Nick.DishProject.controller;


import com.Nick.DishProject.dto.Amount;
import com.Nick.DishProject.dto.DishDto;
import com.Nick.DishProject.exception.DishIngredientNotFoundException;
import com.Nick.DishProject.model.*;
import com.Nick.DishProject.service.DishIngredientService;
import com.Nick.DishProject.service.DishService;
import com.Nick.DishProject.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/dish")
public class DishController {

    private final DishService dishService;
    private final DishIngredientService dishIngredientService;
    private final IngredientService ingredientService;

    public DishController(DishService dishService,
                          DishIngredientService dishIngredientService,
                          IngredientService ingredientService) {
        this.dishService=dishService;
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
                dto.add(this.mapDishToDto(d));
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
    public ResponseEntity<Dish> addDish(@Valid @RequestBody Dish dish) {

        Dish newDish = dishService.addDish(dish);
        return new ResponseEntity<Dish>(newDish, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Dish> updateDish(@Valid @RequestBody Dish dish) {
        Dish newDish = dishService.updateDish(dish);
        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PutMapping("/updatedto")
    @Transactional
    public ResponseEntity<Dish> updateDishDto(@Valid @RequestBody DishDto dishDto) {

        Dish dish = this.mapDtoToDish(dishDto);

        Iterator<DishIngredient> iter = dish.getIngredients().iterator();
        Set<DishIngredient> newIngredients = new HashSet<>();

        //Loop through all incoming Ingredients of the Dish
        while(iter.hasNext()) {
            DishIngredient dI = iter.next();

            //Make DishIngredientId to attempt a fetch of DishIngredient,
            DishIngredientId id = new DishIngredientId(dI.getDish().getId(), dI.getIngredient().getId());
            try {
                DishIngredient oldDI = dishIngredientService.findDishIngredientById(id);
                //if exists -> overwrite the old amount and add it to a list of fresh DishIngredients
                oldDI.setAmountNeeded(dI.getAmountNeeded());
                newIngredients.add(oldDI);
            } catch(DishIngredientNotFoundException e) {
                //if not exists -> add current dishIngredient to list without modifications
                newIngredients.add(dI);
            }
        }

        //Wipe ALL current DishIngredients from db to prevent issues with persistence
        List<DishIngredient> wipe = dishIngredientService.findDishIngredientsByDish(dish);
        for(DishIngredient wipeDishIngredient : wipe) {
            if(!newIngredients.contains(wipeDishIngredient)) {
                dishIngredientService.deleteDishIngredientById(wipeDishIngredient.getId());
            }
        }


        dish.setIngredients(newIngredients);

        this.updateUpdateIngredientMapping(dish.getIngredients());
        dishService.updateDish(dish);

        return new ResponseEntity<>(dish, HttpStatus.OK);
    }

    @PostMapping("/adddto")
    @Transactional
    public ResponseEntity<Dish> addDishDto(@Valid @RequestBody DishDto dishDto) {

        Dish dish = this.mapDtoToDish(dishDto);
        dishService.addDish(dish);
        this.addUpdateIngredientMapping(dish.getIngredients());

        return new ResponseEntity<>(dish, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteDishById(@PathVariable("id")Long id) {
        dishService.deleteDishById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private DishDto mapDishToDto(Dish dish) {
        DishDto dishDto = new DishDto();
        dishDto.setId(dish.getId());
        dishDto.setName(dish.getName());
        dishDto.setAvgTimeToMake(dish.getAvgTimeToMake());
        dishDto.setCalories(dish.getCalories());
        dishDto.setRating(dish.getRating());
        dishDto.setWarm(dish.isWarm());
        dishDto.setImage(dish.getImage());
        dishDto.setDescription(dish.getDescription());
        dishDto.setLongDescription(dish.getLongDescription());
        dishDto.setDiets(dish.getDiets());
        dishDto.setCategories(dish.getCategories());

        Set<Ingredient> ingredients = new HashSet<>();
        Amount amount = new Amount();

        if (dish.getIngredients() != null && !dish.getIngredients().isEmpty()) {
            for (DishIngredient di : dish.getIngredients()) {
                ingredients.add(di.getIngredient());
                amount.getIng().add(di.getIngredient().getId().intValue());
                amount.getAm().add(di.getAmountNeeded());
            }
        }
        dishDto.setIngredients(ingredients);
        dishDto.setAmount(amount);

        return dishDto;
    }

    private Dish mapDtoToDish(DishDto dishDto) {
        Dish dish = new Dish();

        dish.setId(dishDto.getId());
        dish.setName(dishDto.getName());
        dish.setAvgTimeToMake(dishDto.getAvgTimeToMake());
        dish.setCalories(dishDto.getCalories());
        dish.setRating(dishDto.getRating());
        dish.setWarm(dishDto.isWarm());
        dish.setImage(dishDto.getImage());
        dish.setDescription(dishDto.getDescription());
        dish.setLongDescription(dishDto.getLongDescription());
        dish.setDiets(dishDto.getDiets());
        dish.setCategories(dishDto.getCategories());

        Set<Ingredient> ingredients = dishDto.getIngredients();

        if(ingredients != null) {
            for(Ingredient ingredient : ingredients) {

                DishIngredient dI = new DishIngredient();

                String amount = "";


                loop:for (int i = 0;i<dishDto.getAmount().getIng().size();i++ ) {
                    if(dishDto.getAmount().getIng().get(i) == ingredient.getId().intValue()) {
                        amount = dishDto.getAmount().getAm().get(i);
                    }
                }

                dI.setAmountNeeded(amount);

                dish.getIngredients().add(dI);
                ingredient.getDishes().add(dI);

                dI.setIngredient(ingredient);
                dI.setDish(dish);


            }
        }
        return dish;
    }

    private void addUpdateIngredientMapping(Set<DishIngredient> dIs) {
        for(DishIngredient dishIngredient : dIs) {
            this.dishIngredientService.addDishIngredient(dishIngredient);
            this.ingredientService.updateIngredient(dishIngredient.getIngredient());
        }
    }

    private void updateUpdateIngredientMapping(Set<DishIngredient> dIs) {
        for (DishIngredient dishIngredient : dIs) {
            this.dishIngredientService.updateDishIngredient(dishIngredient);
            this.ingredientService.updateIngredient(dishIngredient.getIngredient());
        }
    }
}