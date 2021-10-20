package com.Nick.DishProject.validation;

import com.Nick.DishProject.dto.DishDto;
import com.Nick.DishProject.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createDishDto;
import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createIngredient;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IngredientModelValidation {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testIngredientSuccess() {
        Ingredient ingredient = createIngredient("Test1","ShouldSucceed");
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testIngredient_BlankNameShouldFail() {
        Ingredient ingredient = createIngredient("", "ShouldFail");
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        for(ConstraintViolation violation : violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    void testIngredient_TooLongNameShouldFail() {
        Ingredient ingredient = createIngredient("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq",
                "ShouldFial");
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        for(ConstraintViolation violation : violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    void testIngredient_BlankTypeShouldFail() {
        Ingredient ingredient = createIngredient("ShouldFail",
                "");
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        for(ConstraintViolation violation : violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }
    @Test
    void testIngredient_TooLongTypeShouldFail() {
        Ingredient ingredient = createIngredient("shouldfail",
                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        Set<ConstraintViolation<Ingredient>> violations = validator.validate(ingredient);
        for(ConstraintViolation violation : violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }
}
