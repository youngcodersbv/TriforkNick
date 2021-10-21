package com.Nick.DishProject.controller;


import com.Nick.DishProject.dto.Amount;
import com.Nick.DishProject.dto.DishDto;
import com.Nick.DishProject.exception.DishIngredientNotFoundException;
import com.Nick.DishProject.model.*;
import com.Nick.DishProject.service.DishIngredientService;
import com.Nick.DishProject.service.DishService;
import com.Nick.DishProject.service.IngredientService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(DishController.class)
public class DishControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @MockBean
    DishService dishService;
    @MockBean
    DishIngredientService dishIngredientService;
    @MockBean
    IngredientService ingredientService;

    @Autowired
    private MockMvc mvc;


    @Test
    public void testMvcGetAllDishes_ReturnsAllDishesAsJSONDTO() throws Exception {
        List<Dish> dishes = new ArrayList<>();
        dishes.add(createDish(null,null,"dish1"));
        dishes.add(createDish(null,null, "dish2"));

        String expected = JSONArray.toJSONString(dishes);

        when(dishService.findAllFilteredDishes(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(dishes);

        RequestBuilder request = MockMvcRequestBuilders.get("/dish/all");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(dishes.get(0).getName());
        assertThat(result.getResponse().getContentAsString()).contains(Integer.toString(dishes.get(0).getCalories()));
        assertThat(result.getResponse().getContentAsString()).contains(dishes.get(1).getName());
        assertThat(result.getResponse().getContentAsString()).contains(Integer.toString(dishes.get(1).getCalories()));


        //means it was successfully put into DTO-form
        assertThat(result.getResponse().getContentAsString()).contains("\"ing\"").contains("\"am\"");

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).findAllFilteredDishes(ArgumentMatchers.any(),ArgumentMatchers.any());
    }

    @Test
    public void testMvcGetAllDishesInclIngredients_ReturnsAllAsJSONDTO() throws Exception {
        List<Dish> dishes = new ArrayList<>();
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = createIngredient("Testname","TestType");
        ingredient.setId(0L);
        ingredients.add(ingredient);

        Dish dish = createDish(null,null, "Hello");
        dish.setId(1L);
        dish.setDescription("Testdesc");

        DishIngredient dI = createDishIngredient(ingredient,dish);
        dI.setAmountNeeded("54321");
        Set<DishIngredient> dIs = new HashSet<>();
        dIs.add(dI);
        dish.setIngredients(dIs);
        dishes.add(dish);


        when(dishService.findAllFilteredDishes(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(dishes);

        RequestBuilder request = MockMvcRequestBuilders.get("/dish/all");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(dishes.get(0).getName());
        assertThat(result.getResponse().getContentAsString()).contains(Integer.toString(dishes.get(0).getCalories()));


        //means it was successfully put into DTO-form
        assertThat(result.getResponse().getContentAsString()).contains("\"ing\"");
        assertThat(result.getResponse().getContentAsString()).contains("\"am\"");

        assertThat(result.getResponse().getContentAsString()).contains("54321");

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).findAllFilteredDishes(ArgumentMatchers.any(),ArgumentMatchers.any());
    }

    @Test
    public void testMvcGetAllDishesWithFilter_PassesValuesCorrectly() throws Exception {
        List<Dish> dishes = new ArrayList<>();

        dishes.add(createDish(null,null,"dish1"));
        dishes.add(createDish(null,null, "dish2"));

        when(dishService.findAllFilteredDishes(ArgumentMatchers.any(List.class), ArgumentMatchers.any(List.class)))
                .thenReturn(new ArrayList<Dish>());

        RequestBuilder request = MockMvcRequestBuilders.get("/dish/all?category=Pizza&category=Pasta&diet=Regular&diet=Keto");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(new ArrayList<>().toString());

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).findAllFilteredDishes(ArgumentMatchers.any(),ArgumentMatchers.any());
    }

    @Test
    void testMvcFindById_ReturnsDish() throws Exception{
        Dish dish = createDish(null,null,"TestDish");

        when(dishService.findDishById(ArgumentMatchers.any(Long.class))).thenReturn(dish);

        RequestBuilder request = MockMvcRequestBuilders.get("/dish/find/1");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(dish.getName());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).findDishById(ArgumentMatchers.any(Long.class));
    }

    @Test
    public void testMvcAddDishDto_ReturnsDish() throws Exception{
        DishDto dishDto = new DishDto();
        dishDto.setDescription("TestDesc");
        dishDto.setId(1L);
        dishDto.setName("Hello");

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setDescription("TestDesc");
        dish.setName("Hello");

        when(dishService.addDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        RequestBuilder request = MockMvcRequestBuilders.post("/dish/adddto")
                .contentType(APPLICATION_JSON_UTF8).content(JSONValue.toJSONString(dishDto));
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(Long.toString(dish.getId()));

        //means it is no longer DTO
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"ing\"");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"am\"");

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verify(dishService,times(1)).addDish(ArgumentMatchers.any(Dish.class));
    }

    //TODO test adddto with ingredients mapped

    @Test
    public void testMvcAddDishDtoWithIngredients_ReturnsDish() throws Exception {
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = createIngredient("Testname","TestType");
        ingredient.setId(0L);
        ingredients.add(ingredient);
        Amount amount = new Amount();
        List<Integer> ingAmount = new ArrayList<>();
        ingAmount.add(0);

        amount.setIng(ingAmount);
        List<String> numAmount = new ArrayList<>();
        numAmount.add("54321");
        amount.setAm(numAmount);

        DishDto dishDto = new DishDto();
        dishDto.setDescription("Testdesc");
        dishDto.setId(1L);
        dishDto.setName("Hello");
        dishDto.setIngredients(ingredients);
        dishDto.setAmount(amount);

        Dish dish = createDish(null,null, "Hello");
        dish.setId(1L);
        dish.setDescription("Testdesc");

        when(dishService.addDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        RequestBuilder request = MockMvcRequestBuilders.post("/dish/adddto")
                .contentType(APPLICATION_JSON_UTF8).content(JSONValue.toJSONString(dishDto));

        MvcResult result = mvc.perform(request).andReturn();

        //Assert that response is not DTO
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"ing\"");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"am\"");

        //Assert that dish contains the ingredient
        assertThat(result.getResponse().getContentAsString()).contains("TestType");

        assertThat(result.getResponse().getContentAsString()).contains("54321");

        assertThat(result.getResponse().getContentAsString()).contains(dish.getName());
    }


    @Test
    public void testMvcUpdateDishDto_ReturnsDish() throws Exception {
        DishDto dishDto = new DishDto();
        dishDto.setDescription("Testdesc");
        dishDto.setId(1L);
        dishDto.setName("Hello");

        Dish dish = new Dish();
        dish.setId(1L);
        dish.setName("Hello");
        dish.setDescription("Testdesc");

        when(dishService.findDishById(ArgumentMatchers.any(Long.class))).thenReturn(dish);
        when(dishService.updateDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        RequestBuilder request = MockMvcRequestBuilders.put("/dish/updatedto")
                .contentType(APPLICATION_JSON_UTF8).content(JSONValue.toJSONString(dishDto));
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(Long.toString(dish.getId()));


        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"ing\"");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"am\"");

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).updateDish(ArgumentMatchers.any(Dish.class));
    }

    //TODO test updatedto with ingredients mapped

    @Test
    void testMvcUpdateDishInclIngredients_ReturnsDish() throws Exception {
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient ingredient = createIngredient("Testname","TestType");
        ingredient.setId(0L);
        ingredients.add(ingredient);
        Amount amount = new Amount();
        List<Integer> ingAmount = new ArrayList<>();
        ingAmount.add(0);

        amount.setIng(ingAmount);
        List<String> numAmount = new ArrayList<>();
        numAmount.add("54321");
        amount.setAm(numAmount);

        DishDto dishDto = new DishDto();
        dishDto.setDescription("Testdesc");
        dishDto.setId(1L);
        dishDto.setName("Hello");
        dishDto.setIngredients(ingredients);
        dishDto.setAmount(amount);

        Dish dish = createDish(null,null, "Hello");
        dish.setId(1L);
        dish.setDescription("Testdesc");

        when(dishIngredientService.findDishIngredientById(ArgumentMatchers.any(DishIngredientId.class))).thenThrow(new DishIngredientNotFoundException("Errorrr"));
        when(dishService.findDishById(ArgumentMatchers.any(Long.class))).thenReturn(dish);
        when(dishService.updateDish(ArgumentMatchers.any(Dish.class))).thenReturn(dish);

        RequestBuilder request = MockMvcRequestBuilders.put("/dish/updatedto")
                .contentType(APPLICATION_JSON_UTF8).content(JSONValue.toJSONString(dishDto));

        MvcResult result = mvc.perform(request).andReturn();

        //Assert that response is not DTO
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"ing\"");
        assertThat(result.getResponse().getContentAsString()).doesNotContain("\"am\"");

        //Assert that dish contains the ingredient
        assertThat(result.getResponse().getContentAsString()).contains("TestType");

        assertThat(result.getResponse().getContentAsString()).contains("54321");

        assertThat(result.getResponse().getContentAsString()).contains(dish.getName());
    }


    @Test
    void testMvcDeleteDishById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/dish/delete/1");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dishService,times(1)).deleteDishById(1L);
    }
}
