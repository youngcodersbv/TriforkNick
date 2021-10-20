package com.Nick.DishProject.testUtil;

import com.Nick.DishProject.dto.DishDto;
import com.Nick.DishProject.model.*;

import java.util.HashSet;
import java.util.Set;

public class ObjectInstantiationUtil {

    public static Diet createDiet(String dietName) {
        Diet diet = new Diet();
        diet.setType(dietName);
        return diet;
    }

    public static DishIngredient createDishIngredient(Ingredient ingredient, Dish dish) {
        DishIngredient dishIngredient = new DishIngredient();
        dishIngredient.setIngredient(ingredient);
        dishIngredient.setDish(dish);
        dishIngredient.setAmountNeeded("1 something");

        return dishIngredient;
    }

    public static Category createCategory(String categoryName) {
        Category category = new Category();
        category.setType(categoryName);
        return category;
    }

    public static Ingredient createIngredient(String ingredientName, String ingredientType) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientName);
        ingredient.setType(ingredientType);
        return ingredient;
    }

    public static Dish createDish(Set<Diet> diets, Set<Category> categories, String dishName) {
        Dish dish = new Dish();

        if(diets == null) diets = new HashSet<>();
        if(categories == null) categories = new HashSet<>();

        dish.setDiets(diets);
        dish.setCategories(categories);
        dish.setName(dishName);
        dish.setAvgTimeToMake(10);
        dish.setCalories(100);
        dish.setDescription("Definitely something");
        dish.setWarm(true);
        dish.setCategories(categories);
        dish.setRating(10);
        dish.setImage("");
        dish.setLongDescription("Definitely definitely something");

        return dish;
    }

    public static DishDto createDishDto(String name, int rating, int calories, int avg) {
        DishDto dishDto = new DishDto();
        dishDto.setName(name);
        dishDto.setWarm(true);
        dishDto.setRating(rating);
        dishDto.setCalories(calories);
        dishDto.setAvgTimeToMake(avg);
        return dishDto;
    }
}
