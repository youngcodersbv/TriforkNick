package com.Nick.DishProject.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"dishes"})
@Table(name = "diet", schema = "public")
public class Diet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private DietType type;

    @ManyToMany(mappedBy = "diets")
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
