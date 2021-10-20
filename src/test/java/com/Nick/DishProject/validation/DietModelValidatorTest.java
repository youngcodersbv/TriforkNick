package com.Nick.DishProject.validation;

import com.Nick.DishProject.model.Diet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createDiet;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DietModelValidatorTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDietSuccess() {
        Diet diet = createDiet("Success");
        Set<ConstraintViolation<Diet>> violations = validator.validate(diet);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testTypeEmptyShouldFailValidation() {
        Diet diet = createDiet("");
        Set<ConstraintViolation<Diet>> violations = validator.validate(diet);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
            //type must not be blank
        }
        assertFalse(violations.isEmpty());
    }
}
