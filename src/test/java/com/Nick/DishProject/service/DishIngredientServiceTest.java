package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DishIngredientNotFoundException;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.model.DishIngredient;
import com.Nick.DishProject.model.DishIngredientId;
import com.Nick.DishProject.repository.DishIngredientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishIngredientServiceTest {

    @Mock
    private DishIngredientRepository dishIngredientRepository;
    private DishIngredientService dishIngredientService;

    @BeforeEach
    void setUp() {
        dishIngredientService = new DishIngredientService(dishIngredientRepository);
    }

    @AfterEach
    void tearDown() {
        //
    }

    @Test
    public void findAllDishIngredients_ReturnsAllDishIngredients() {
        List<DishIngredient> dishIngredients = new ArrayList<>();
        dishIngredients.add(createDishIngredient(
                createIngredient("Test","TestCat"),
                createDish(null,null,"Test")));

        when(dishIngredientRepository.findAll()).thenReturn(dishIngredients);

        List<DishIngredient> result = dishIngredientService.findAllDishIngredients();
        assertThat(result.get(0).getDish()).isSameAs(dishIngredients.get(0).getDish());
        verify(dishIngredientRepository,times(1)).findAll();
    }

    @Test
    public void addDishIngredient_ReturnsDishIngredient() {
        DishIngredient dishIngredient = createDishIngredient(
                createIngredient("Test","TestCat"),
                createDish(null,null,"Test"));

        when(dishIngredientRepository.save(ArgumentMatchers.any(DishIngredient.class)))
                .thenReturn(dishIngredient);

        DishIngredient result = dishIngredientService.addDishIngredient(dishIngredient);

        assertThat(result.getDish()).isSameAs(dishIngredient.getDish());
        verify(dishIngredientRepository,times(1)).save(dishIngredient);
    }

    @Test
    public void updateDishIngredient_ReturnsDishIngredient() {
        DishIngredient dishIngredient = createDishIngredient(
                createIngredient("Test","TestCat"),
                createDish(null,null,"Test"));

        when(dishIngredientRepository.save(ArgumentMatchers.any(DishIngredient.class)))
                .thenReturn(dishIngredient);

        DishIngredient result = dishIngredientService.updateDishIngredient(dishIngredient);

        assertThat(result.getDish()).isSameAs(dishIngredient.getDish());
        verify(dishIngredientRepository,times(1)).save(dishIngredient);
    }

    @Test
    public void deleteDishIngredient() {
        dishIngredientService.deleteDishIngredientById(new DishIngredientId());
        verify(dishIngredientRepository,times(1)).deleteById(new DishIngredientId());
    }

    @Test
    public void findDishIngredientByDish_ReturnsListOfDishIngredients() {
        Dish dish = createDish(null, null, "Test");
        DishIngredient dishIngredient = createDishIngredient(
                createIngredient("TestName", "TestType"),
                dish);
        List<DishIngredient> dishIngredients = new ArrayList<>();
        dishIngredients.add(dishIngredient);

        when(dishIngredientRepository.findDishIngredientsByDish(ArgumentMatchers.any(Dish.class))).thenReturn(dishIngredients);

        List<DishIngredient> result = dishIngredientService.findDishIngredientsByDish(dish);

        assertThat(result.get(0).getDish()).isSameAs(dishIngredients.get(0).getDish());
        verify(dishIngredientRepository,times(1)).findDishIngredientsByDish(ArgumentMatchers.any(Dish.class));
    }

    @Test
    public void findDishIngredientById_ReturnsDishIngredient() {
        DishIngredient dishIngredient = createDishIngredient(
                createIngredient("Test","TestCat"),
                createDish(null,null,"Test"));
        Optional<DishIngredient> optionalDishIngredient = Optional.of(dishIngredient);

        when(dishIngredientRepository.findById(new DishIngredientId()))
                .thenReturn(optionalDishIngredient);

        DishIngredient result = dishIngredientService.findDishIngredientById(new DishIngredientId());

        assertThat(result.getDish()).isSameAs(dishIngredient.getDish());
        verify(dishIngredientRepository,times(1)).findById(new DishIngredientId());
    }

    @Test
    public void findDishIngredientById_DoesNotExistThrowsException() {
        Optional<DishIngredient> optionalDishIngredient = Optional.empty();

        when(dishIngredientRepository.findById(ArgumentMatchers.any(DishIngredientId.class)))
                .thenReturn(optionalDishIngredient);

        Assertions.assertThrows(DishIngredientNotFoundException.class, () -> {
            dishIngredientService.findDishIngredientById(new DishIngredientId());
        });
    }
}
