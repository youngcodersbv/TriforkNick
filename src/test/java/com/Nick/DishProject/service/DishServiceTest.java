package com.Nick.DishProject.service;

import com.Nick.DishProject.model.Dish;
import com.Nick.DishProject.repository.DishRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

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
    void filterDishByDishType() {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish());
        dishes.add(createSecondDish());

        when(dishService.findAllDishes()).thenReturn(dishes);

        List<Dish> result = dishService.findAllFilteredDishes(Dish.DishType.ASIAN,null);
        assertThat(dishes.size() != result.size());

        result = dishService.findAllFilteredDishes(Dish.DishType.PIZZA,null);
        assertThat(result.size() == 0);

        result = dishService.findAllFilteredDishes(null,null);
        assertThat(dishes.equals(result));
    }

    private Dish createDish() {
        Dish dish = new Dish();
        dish.setName("Something");
        dish.setAvgTimeToMake(10);
        dish.setCalories(100);
        dish.setDescription("Definitely something");
        dish.setWarm(true);
        dish.setType(Dish.DishType.ASIAN);
        dish.setRating(10);
        dish.setImgPath("");
        dish.setLongDescription("Definitely definitely something");
        return dish;
    }
    private Dish createSecondDish() {
        Dish dish = new Dish();
        dish.setName("Something");
        dish.setAvgTimeToMake(10);
        dish.setCalories(100);
        dish.setDescription("Definitely something");
        dish.setWarm(true);
        dish.setType(Dish.DishType.MEDITERRANEAN);
        dish.setRating(10);
        dish.setImgPath("");
        dish.setLongDescription("Definitely definitely something");
        return dish;
    }
}