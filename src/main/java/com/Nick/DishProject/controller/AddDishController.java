package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/addDish")
public class AddDishController {

    final DishRepository dishRepository;

    public AddDishController(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    @GetMapping
    public String dishLanding() {
        return "addDish";
    }

    @PostMapping
    public String addDish(@ModelAttribute Dish dish) {
        System.out.println(dish.toString());
        dishRepository.save(dish);
        return "redirect:/dishes";
    }
}
