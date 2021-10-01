package com.Nick.DishProject.dto;

import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.model.Dish;

public class DishDTO {
    private Diet.DietType diet;
    private Long dish_id;

    public Diet.DietType getDiet() {
        return diet;
    }

    public void setDiet(Diet.DietType diet) {
        this.diet = diet;
    }

    public Long getDish_id() {
        return dish_id;
    }

    public void setDish_id(Long dish_id) {
        this.dish_id = dish_id;
    }
}
