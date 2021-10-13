package com.Nick.DishProject.model;

import com.Nick.DishProject.exception.DishIngredientNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Fetch;
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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @MapsId("ingredientId")
    private Ingredient ingredient;

    @Column(nullable = false)
    private String amountNeeded;

    public DishIngredientId getId() {
        return id;
    }

    public void setId(DishIngredientId id) { this.id = id; }

    public String getAmountNeeded() {
        return amountNeeded;
    }

    public void setAmountNeeded(String amountNeeded) {
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
