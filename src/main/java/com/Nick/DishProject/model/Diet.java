package com.Nick.DishProject.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "diet", schema = "public")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private DietType type;

    @ManyToMany
    @JoinTable(
            name = "dish_diet",
            joinColumns = @JoinColumn(name="diet_id"),
            inverseJoinColumns = @JoinColumn(name="dish_id"))
    private Set<Dish> dishes;

    public Long getId() {
        return id;
    }

    public DietType getType() {
        return type;
    }

    public void setType(DietType type) {
        this.type = type;
    }

    public Set<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Set<Dish> dishes) {
        this.dishes = dishes;
    }

    public enum DietType{
        VEGETARIAN,
        VEGAN,
        PESCETERIAN,
        REGULAR,
        GLUTEN_FREE
    }
}
