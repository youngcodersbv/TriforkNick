package com.Nick.DishProject.validation;

import com.Nick.DishProject.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.*;

import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createCategory;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryModelValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testCategorySuccess() {
        Category category = createCategory("Success");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testTypeEmptyShouldFailValidation() {
        Category category = createCategory("");
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
            //type must not be blank
        }
        assertFalse(violations.isEmpty());
    }
}
