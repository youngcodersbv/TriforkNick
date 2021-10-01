package com.Nick.DishProject.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class DishIngredient {

    @Id
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @JsonBackReference
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
