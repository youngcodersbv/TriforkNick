package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.service.CategoryService;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
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

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createCategory;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));


    @MockBean
    CategoryService categoryService;

    @Autowired
    private MockMvc mvc;

    @Test
    public void testMvcGetAllCategories_ReturnsAllCategoriesAsJSON() throws Exception {
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory("Asian"));
        categories.add(createCategory("Mediterranean"));

        //manually removing ""dishes":null" because @JsonIgnoreProperties
        String expected = JSONArray.toJSONString(categories)
                .replace("\"dishes\":null,","");

        when(categoryService.findAllCategories()).thenReturn(categories);


        RequestBuilder request = MockMvcRequestBuilders.get("/category/all");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(expected).isEqualTo(result.getResponse().getContentAsString());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(categoryService,times(1)).findAllCategories();
    }

    @Test
    public void testMvcAddCategory_ReturnsCategoryAsJSON() throws Exception {
        Category category = createCategory("Asian");
        String jsonValue = JSONValue.toJSONString(category);
        String expected = JSONValue.toJSONString(category)
                .replace("\"dishes\":null,","");

        when(categoryService.addCategory(ArgumentMatchers.any(Category.class))).thenReturn(category);

        RequestBuilder request = MockMvcRequestBuilders.post("/category/add")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verify(categoryService,times(1)).addCategory(ArgumentMatchers.any());
    }

    @Test
    public void testMvcUpdateCategory_ReturnsCategoryAsJSON() throws Exception {
        Category category = createCategory("Asian");
        String jsonValue = JSONValue.toJSONString(category);
        String expected = JSONValue.toJSONString(category)
                .replace("\"dishes\":null,","");

        when(categoryService.updateCategory(ArgumentMatchers.any(Category.class))).thenReturn(category);

        RequestBuilder request = MockMvcRequestBuilders.put("/category/update")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(categoryService,times(1)).updateCategory(ArgumentMatchers.any(Category.class));
    }



    @Test
    public void testMvcDeleteById() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders.delete("/category/delete/1");
        MvcResult result = mvc.perform(request).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(categoryService,times(1)).deleteCategoryById(ArgumentMatchers.any());
    }
}
