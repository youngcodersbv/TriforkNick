package com.Nick.DishProject.validation;

import com.Nick.DishProject.dto.DishDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createDishDto;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DishDtoModelValidatorTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testDishDtoSuccess() {
        DishDto dishDto = createDishDto("Name",10,10,10);

        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testEmptyNameShouldFail() {
        DishDto dishDto = createDishDto("",10,10,10);
        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    void testNameTooLongShouldFail() {
        DishDto dishDto = createDishDto("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq",
                10,10,10);
        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        for(ConstraintViolation violation : violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testRatingOutside1_10ShouldFail() {
        DishDto dishDto = createDishDto("Name",100,10,10);
        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testCaloriesBelow0ShouldFail() {
        DishDto dishDto = createDishDto("Name",10,-10,10);
        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testAvgBelow0ShouldFail() {
        DishDto dishDto = createDishDto("Name", 10,10,-10);
        Set<ConstraintViolation<DishDto>> violations = validator.validate(dishDto);
        for(ConstraintViolation violation:violations) {
            System.out.println(violation.getPropertyPath().toString()+" "+
                    violation.getMessage());
        }
        assertFalse(violations.isEmpty());
    }
}
