package com.Nick.DishProject.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"dishes"})
@Table(name = "Ingredient", schema="public")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    @NotBlank
    @Size(max = 64)
    private String name;

    @Column
    @NotNull
    @NotBlank
    @Size(max = 64)
    private String type;

    @OneToMany(mappedBy = "ingredient")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<DishIngredient> dishes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) { this.id=id; }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Set<DishIngredient> getDishes() {
        return dishes;
    }

    public void setDishes(Set<DishIngredient> dishes) {
        this.dishes = dishes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
