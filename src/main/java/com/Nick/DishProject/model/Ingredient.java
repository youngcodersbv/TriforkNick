package com.Nick.DishProject.model;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "Ingredient", schema="public")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private IngredientType type;

    @OneToMany(mappedBy = "ingredient")
    private Set<DishIngredient> dishes;

    public Long getId() {
        return id;
    }

    public IngredientType getType() {
        return type;
    }

    public void setType(IngredientType type) {
        this.type = type;
    }

    public Set<DishIngredient> getDishes() {
        return dishes;
    }

    public void setDishes(Set<DishIngredient> dishes) {
        this.dishes = dishes;
    }

    public enum IngredientType {
        VEGETABLE,
        FRUIT,
        MEAT,
        FISH,
        SPICE
    }
}
