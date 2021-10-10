package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.service.DietService;
import com.Nick.DishProject.service.IngredientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/diet")
public class DietController {

    private DietService dietService;

    public DietController(DietService dietService) {
        this.dietService = dietService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Diet>> getAllDiets() {
        List<Diet> diets = dietService.findAllDiets();
        return new ResponseEntity<>(diets, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Diet> addDiet(@RequestBody Diet diet) {
        Diet newDiet = dietService.addDiet(diet);
        return new ResponseEntity<>(newDiet,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Diet> updateDiet(@RequestBody Diet diet) {
        Diet newDiet = dietService.updateDiet(diet);
        return new ResponseEntity<>(newDiet, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteDietById(@PathVariable("id")Long id) {
        dietService.deleteDietById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
