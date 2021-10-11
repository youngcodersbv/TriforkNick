package com.Nick.DishProject.dto;

import com.Nick.DishProject.model.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DishDto {

    private Long id;
    private String name;
    private int avgTimeToMake;
    private int calories;
    private int rating;
    private boolean warm;
    private String imgPath;
    private String description;
    private String longDescription;
    private Set<Ingredient> ingredients;
    private Set<Integer> amount;
    private Set<Diet> diets;
    private Set<Category> categories;

    public DishDto() {}

    public DishDto(Dish dish) {
        this.id=dish.getId();
        this.name=dish.getName();
        this.avgTimeToMake = dish.getAvgTimeToMake();
        this.calories=dish.getCalories();
        this.rating = dish.getRating();
        this.warm = dish.isWarm();
        this.imgPath=dish.getImgPath();
        this.description=dish.getDescription();
        this.longDescription=dish.getLongDescription();
        this.diets=dish.getDiets();
        this.categories=dish.getCategories();

        Set<Ingredient> ingredients = new HashSet<>();
        if(dish.getIngredients() != null && !dish.getIngredients().isEmpty()) {
            for(DishIngredient di : dish.getIngredients()) {
                ingredients.add(di.getIngredient());
            }
        }

        this.ingredients=ingredients;
    }

    public Dish createDish() {
        Dish dish = new Dish();
        dish.setId(this.id);
        dish.setName(this.name);
        dish.setAvgTimeToMake(this.avgTimeToMake);
        dish.setCalories(this.calories);
        dish.setRating(this.rating);
        dish.setWarm(this.warm);
        dish.setImgPath(this.imgPath);
        dish.setDescription(this.description);
        dish.setLongDescription(this.longDescription);
        dish.setDiets(this.diets);
        dish.setCategories(this.categories);

        return dish;
    }

    public Long getId() {
        return id;
    }

    public Set<Integer> getAmount() {
        return amount;
    }

    public void setAmount(Set<Integer> amount) {
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAvgTimeToMake() {
        return avgTimeToMake;
    }

    public void setAvgTimeToMake(int avgTimeToMake) {
        this.avgTimeToMake = avgTimeToMake;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isWarm() {
        return warm;
    }

    public void setWarm(boolean warm) {
        this.warm = warm;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Diet> getDiets() {
        return diets;
    }

    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
}
