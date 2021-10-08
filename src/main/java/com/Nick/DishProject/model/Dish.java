package com.Nick.DishProject.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Locale;
import java.util.Set;

@Entity
@Table(name = "dish", schema="public")
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int avgTimeToMake;

    @Column(nullable = false)
    private int calories;

    @Column(nullable = true)
    private int rating;

    @Column(nullable = false)
    private boolean warm;

    @Column
    private String imgPath;

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String longDescription;

    @OneToMany(mappedBy = "dish")
    private Set<DishIngredient> ingredients;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "dish_diet",
            joinColumns = @JoinColumn(name="dish_id"),
            inverseJoinColumns = @JoinColumn(name="diet_id"))
    private Set<Diet> diets;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "dish_category",
            joinColumns = @JoinColumn(name="dish_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
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

    public Set<DishIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<DishIngredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Diet> getDiets() {
        return diets;
    }

    public void setDiets(Set<Diet> diets) {
        this.diets = diets;
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

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public boolean hasType(Category category) {
        for(Category c : categories) {
            if(c == category) return true;
        }
        return false;
    }

    public boolean isOfDiet(String dietType) {
        for(Diet diet : diets) {
            if(diet.getType().toLowerCase(Locale.ROOT).equals(dietType.toLowerCase(Locale.ROOT))) return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "id:" + id +
                "\nname:"+name+
                "\ntime:"+Integer.toString(avgTimeToMake)+
                "\ncal:"+Integer.toString(calories)+
                "\nrating:"+Integer.toString(rating)+
                "\nwarm:"+warm;
    }
}
