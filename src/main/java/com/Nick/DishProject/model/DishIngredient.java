package com.Nick.DishProject.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DishIngredient {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;


    @ManyToOne
    @JoinColumn(name="ingredient_id")
    private Ingredient ingredient;

    @Column(nullable = false)
    private int amountNeeded;

    public Long getId() {
        return id;
    }

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(int amountNeeded) {
        this.amountNeeded = amountNeeded;
    }
}
