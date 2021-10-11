package com.Nick.DishProject.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Entity(name="Dish")
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

    @Lob
    @Column
    private String image;

    @Column
    private String description;

    @Column(columnDefinition = "TEXT")
    private String longDescription;

    @OneToMany(mappedBy = "dish")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<DishIngredient> ingredients = new HashSet<>();



    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH
                    })
    @JoinTable(
            name = "dish_diet",
            joinColumns = @JoinColumn(name="dish_id"),
            inverseJoinColumns = @JoinColumn(name="diet_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Diet> diets;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade =
                    {
                            CascadeType.DETACH,
                            CascadeType.MERGE,
                            CascadeType.REFRESH
                    })
    @JoinTable(
            name = "dish_category",
            joinColumns = @JoinColumn(name="dish_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public void setId(Long id) {
        this.id = id;
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
