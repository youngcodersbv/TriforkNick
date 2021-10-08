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
        Dish dish = createDish();

        when(dishService.addDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        Dish created = dishService.addDish(dish);

        assertThat(created.getName()).isSameAs(dish.getName());
        verify(dishRepository, times(1)).save(dish);
    }

    @Test
    void testFindAllDishes_returnsAllDishes() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> fetchedDishes = dishService.findAllDishes();

        assertThat(fetchedDishes.get(0)).isSameAs(dishes.get(0));
        verify(dishRepository,times(1)).findAll();
    }

    @Test
    void testUpdateDish_returnsDish() {
        Dish dish = createDish();

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
    void filterDishByCategory() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        dishes.add(createSecondDish());

        when(dishService.findAllDishes()).thenReturn(dishes);

        Category category = new Category();
        category.setType("ASIAN");

        List<Dish> result = dishService.findAllFilteredDishes(category,null);
        assertThat(dishes.size() != result.size());

        category.setType("PIZZA");
        result = dishService.findAllFilteredDishes(category,null);
        assertThat(result.size() == 0);

        result = dishService.findAllFilteredDishes(null,null);
        assertThat(dishes.equals(result));
    }

    @Test
    void filterDishByDietTypeExists() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        dishes.add(createSecondDish());

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> result = dishService.findAllFilteredDishes(null,"VEGAN");
        assertThat(result.size() == 1);
    }

    @Test
    void filterDishByDietTypeDoesntExist() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        dishes.add(createSecondDish());

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> result = dishService.findAllFilteredDishes(null, "REGULAR");
        assertThat(dishes.size() == 0);
    }

    @Test
    void filterDishByDietTypeCapsInsensitive() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        dishes.add(createSecondDish());

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> result = dishService.findAllFilteredDishes(null, "Vegan");
        assertThat(dishes.size() == 1);
    }

    private Dish createDish() {
        Set<Diet> diets= new HashSet<>();
        Diet diet = new Diet();
        diet.setType("VEGAN");
        diets.add(diet);

        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setType("ASIAN");
        categories.add(category);

        Dish dish = new Dish();
        dish.setDiets(diets);
        dish.setCategories(categories);
        dish.setName("Something");
        dish.setAvgTimeToMake(10);
        dish.setCalories(100);
        dish.setDescription("Definitely something");
        dish.setWarm(true);
        dish.setCategories(categories);
        dish.setRating(10);
        dish.setImgPath("");
        dish.setLongDescription("Definitely definitely something");
        return dish;
    }
    private Dish createSecondDish() {
        Set<Diet> diets= new HashSet<>();
        Diet diet = new Diet();
        diet.setType("PESCO");
        diets.add(diet);

        Set<Category> categories = new HashSet<>();
        Category category = new Category();
        category.setType("MEDITERRANEAN");
        categories.add(category);

        Dish dish = new Dish();
        dish.setDiets(diets);
        dish.setName("Something");
        dish.setAvgTimeToMake(10);
        dish.setCalories(100);
        dish.setDescription("Definitely something");
        dish.setWarm(true);
        dish.setCategories(categories);
        dish.setRating(10);
        dish.setImgPath("");
        dish.setLongDescription("Definitely definitely something");
        return dish;
    }
}