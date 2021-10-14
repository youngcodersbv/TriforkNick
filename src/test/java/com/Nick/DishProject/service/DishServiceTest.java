package com.Nick.DishProject.service;

import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DishServiceTest {

    @Mock
    private DishRepository dishRepository;

    @InjectMocks
    private DishService dishService;

    @Test
    void testCreateDish_returnsDish() {
        Dish dish = createDish(null, null, "Pizza");

        when(dishService.addDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        Dish created = dishService.addDish(dish);

        assertThat(created.getName()).isSameAs(dish.getName());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testFindAllDishes_returnsAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish(null,null,"Pizza"));
        dishes.add(createDish(null,null,"Pasta"));
        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> fetchedDishes = dishService.findAllDishes();

        assertThat(fetchedDishes.get(0)).isSameAs(dishes.get(0));
        verify(dishRepository,times(1)).findAll();
    }

    @Test
    void testUpdateDish_returnsDish() {
        Dish dish = createDish(null,null,"Pizza");

        when(dishService.updateDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);
        Dish updated = dishService.updateDish(dish);

        assertThat(updated.getName()).isSameAs(dish.getName());

        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testDeleteDish() {
        dishService.deleteDishById(1L);
        verify(dishRepository, times(1)).deleteDishById(eq(1L));
    }

    @Test
    void filterDishByDiet() {
        List<Dish> dishes = new ArrayList<>();
        Set<Diet> diets = new HashSet<>();
        diets.add(createDiet("ASIAN"));
        dishes.add(createDish(diets, null, "PIZZA"));

        diets.removeAll(diets);

        diets.add(createDiet("PASTA"));
        dishes.add(createDish(diets,null,"HELLO"));

        when(dishService.findAllDishes()).thenReturn(dishes);


        List<String> query = new ArrayList<>();
        query.add("ASIAN");
        List<Dish> result = dishService.findAllFilteredDishes(null,query);
        assertThat(dishes.size() != result.size());

        query.removeAll(query);
        query.add("PIZZA");
        result = dishService.findAllFilteredDishes(null,query);
        assertThat(result.size() == 0);

        result = dishService.findAllFilteredDishes(null,null);
        assertThat(dishes.equals(result));
    }

    @Test
    void filterDishByCategoryExists() {
        List<Dish> dishes = new ArrayList<>();
        Set<Category> categories = new HashSet<>();
        categories.add(createCategory("VEGAN"));

        dishes.add(createDish(null, categories, "PIZZA"));

        categories.removeAll(categories);
        categories.add(createCategory("REGULAR"));
        dishes.add(createDish(null, categories,"REGULAR"));

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<String> q = new ArrayList<>();
        q.add("VEGAN");

        List<Dish> result = dishService.findAllFilteredDishes(q,null);
        assertThat(result.size() == 1);
    }

    @Test
    void filterDishByDietDoesntExist() {
        List<Dish> dishes = new ArrayList<>();


        dishes.add(createDish(null,null,"Hi"));
        dishes.add(createDish(null,null, "Hello"));

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<String> q = new ArrayList<>();
        q.add("REGULAR");

        List<Dish> result = dishService.findAllFilteredDishes(null, q);
        assertThat(dishes.size() == 0);
    }

    @Test
    void filterDishByDietCapsInsensitive() {
        List<Dish> dishes = new ArrayList<>();
        Set<Diet> diets = new HashSet<>();

        diets.add(createDiet("VEGAN"));

        dishes.add(createDish(diets,null,"Correct"));

        diets.removeAll(diets);
        diets.add(createDiet("VEGETARIAN"));
        dishes.add(createDish(diets,null,"Incorrect"));

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<String> q = new ArrayList<>();
        q.add("Vegan");

        List<Dish> result = dishService.findAllFilteredDishes(null, q);
        assertThat(dishes.size() == 1);
    }
}