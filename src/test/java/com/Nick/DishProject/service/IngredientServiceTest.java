package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.IngredientNotFoundException;
import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.repository.IngredientRepository;
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

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createIngredient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;
    private IngredientService ingredientService;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientService(ingredientRepository);
    }

    @AfterEach
    void tearDown() {
        //
    }

    @Test
    public void testFindAllIngredients_ReturnsAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(createIngredient("Kale","Vegetable"));
        ingredients.add(createIngredient("Salt","Seasoning"));

        when(ingredientService.findAllIngredients()).thenReturn(ingredients);

        List<Ingredient> fetchedIngredients = ingredientService.findAllIngredients();

        assertThat(fetchedIngredients.get(0)).isSameAs(ingredients.get(0));
        verify(ingredientRepository,times(1)).findAll();
    }

    @Test
    public void addIngredient_ReturnsIngredient() {
        Ingredient ingredient = createIngredient("Kale","Vegetable");

        when(ingredientService.addIngredient(ArgumentMatchers.any(Ingredient.class))).thenReturn(ingredient);

        Ingredient result = ingredientService.addIngredient(ingredient);

        assertThat(result.getName()).isSameAs(ingredient.getName());
        verify(ingredientRepository,times(1)).save(ingredient);
    }

    @Test
    public void updateIngredient_ReturnsIngredient() {
        Ingredient ingredient = createIngredient("Kale", "Vegetable");

        when(ingredientService.updateIngredient(ArgumentMatchers.any(Ingredient.class))).thenReturn(ingredient);

        Ingredient result = ingredientService.updateIngredient(ingredient);

        assertThat(result.getName()).isSameAs(ingredient.getName());
        verify(ingredientRepository,times(1)).save(ingredient);
    }

    @Test
    public void deleteIngredient() {
        ingredientService.deleteIngredientById(1L);
        verify(ingredientRepository,times(1)).deleteIngredientById(1L);
    }

    @Test
    void findIngredientById_ReturnsIngredient() {
        Ingredient ingredient = createIngredient("Salt", "Seasoning");

        Optional<Ingredient> optionalIngredient = Optional.of(ingredient);

        when(ingredientRepository.findIngredientById(ArgumentMatchers.any(Long.class))).thenReturn(optionalIngredient);

        Ingredient fetchedIngredient = ingredientService.findIngredientById(1L);

        assertThat(fetchedIngredient.getName()).isSameAs(ingredient.getName());
        verify(ingredientRepository, times(1)).findIngredientById(1L);
    }

    @Test
    void findIngredientById_DoesNotExistThrowsException() {
        Optional<Ingredient> optionalIngredient = Optional.empty();

        when(ingredientRepository.findIngredientById(ArgumentMatchers.any(Long.class))).thenReturn(optionalIngredient);

        Assertions.assertThrows(IngredientNotFoundException.class, () -> {
            ingredientService.findIngredientById(1L);
        });
    }

}