package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Ingredient;
import com.Nick.DishProject.service.IngredientService;
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
import java.util.List;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createDiet;
import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createIngredient;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    IngredientService ingredientService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testMvcGetAllIngredients_ReturnsAllIngredientsAsJSON() throws Exception{
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(createIngredient("ay","oy"));
        ingredients.add(createIngredient("iy","uy"));

        when(ingredientService.findAllIngredients()).thenReturn(ingredients);

        RequestBuilder request = MockMvcRequestBuilders.get("/ingredient/all");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(ingredients.get(0).getName());
        assertThat(result.getResponse().getContentAsString()).contains(ingredients.get(1).getName());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(ingredientService,times(1)).findAllIngredients();
    }

    @Test
    void testMvcAddIngredient_ReturnsIngredientAsJSON() throws Exception {
        Ingredient ingredient = createIngredient("ay","oy");
        String jsonValue = JSONValue.toJSONString(ingredient);

        when(ingredientService.addIngredient(ArgumentMatchers.any(Ingredient.class))).thenReturn(ingredient);

        RequestBuilder request = MockMvcRequestBuilders.post("/ingredient/add")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(ingredient.getName());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verify(ingredientService,times(1)).addIngredient(ArgumentMatchers.any());
    }

    @Test
    void testMvcUpdateIngredient_ReturnsIngredientAsJSON() throws Exception {
        Ingredient ingredient = createIngredient("ay","oy");
        String jsonValue = JSONValue.toJSONString(ingredient);

        when(ingredientService.updateIngredient(ArgumentMatchers.any(Ingredient.class))).thenReturn(ingredient);

        RequestBuilder request = MockMvcRequestBuilders.put("/ingredient/update")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).contains(ingredient.getName());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(ingredientService,times(1)).updateIngredient(ArgumentMatchers.any());
    }

    @Test
    void testMvcDeleteById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/ingredient/delete/1");
        MvcResult result = mvc.perform(request).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(ingredientService,times(1)).deleteIngredientById(ArgumentMatchers.any());
    }
}
