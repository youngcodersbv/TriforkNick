package com.Nick.DishProject.model;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DishIngredientId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dishId;
    private Long ingredientId;

    public DishIngredientId() {}

    public DishIngredientId(Long dishId, Long ingredientId) {
        this.dishId = dishId;
        this.ingredientId = ingredientId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((dishId == null) ? 0 : dishId.hashCode());
        result = prime * result + ((ingredientId == null) ? 0 : ingredientId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null) return false;
        if(getClass() != obj.getClass()) return false;
        DishIngredientId other = (DishIngredientId) obj;
        return Objects.equals(getDishId(),other.getDishId()) &&
                Objects.equals(getIngredientId(),other.getIngredientId());
    }
}
