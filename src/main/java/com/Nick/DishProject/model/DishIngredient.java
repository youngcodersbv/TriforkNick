package com.Nick.DishProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Embeddable
@Entity
@JsonIgnoreProperties({"dish"})
public class DishIngredient {

    @EmbeddedId
    @OnDelete(action=OnDeleteAction.CASCADE)
    private DishIngredientId id = new DishIngredientId();

    @ManyToOne()
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("dishId")
    private Dish dish;

    @ManyToOne(cascade = CascadeType.MERGE)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private int amountNeeded;

    public DishIngredientId getId() {
        return id;
    }

    public int getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(int amountNeeded) {
        this.amountNeeded = amountNeeded;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}
