package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.service.DietService;
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
import java.util.List;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.createDiet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(DietController.class)
public class DietControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    @MockBean
    DietService dietService;

    @Autowired
    private MockMvc mvc;

    @Test
    void testMvcGetAllDiets_ReturnsAllDietsAsJSON() throws Exception{
        List<Diet> diets = new ArrayList<>();
        diets.add(createDiet("KETO"));
        diets.add(createDiet("REGULAR"));

        String expected = JSONArray.toJSONString(diets)
                .replace("\"dishes\":null,","");
        when(dietService.findAllDiets()).thenReturn(diets);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/diet/all");
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(expected).isEqualTo(result.getResponse().getContentAsString());
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dietService,times(1)).findAllDiets();
    }

    @Test
    void testMvcAddDiet_ReturnsDietAsJSON() throws Exception {
        Diet diet = createDiet("KETO");
        String jsonValue = JSONValue.toJSONString(diet);
        String expected = JSONValue.toJSONString(diet)
                .replace("\"dishes\":null,","");

        when(dietService.addDiet(ArgumentMatchers.any(Diet.class))).thenReturn(diet);

        RequestBuilder request = MockMvcRequestBuilders.post("/api/diet/add")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CREATED.value());

        verify(dietService,times(1)).addDiet(ArgumentMatchers.any());
    }

    @Test
    void testMvcUpdateDiet_ReturnsDietAsJSON() throws Exception {
        Diet diet = createDiet("KETO");
        String jsonValue = JSONValue.toJSONString(diet);
        String expected = JSONValue.toJSONString(diet)
                .replace("\"dishes\":null,","");

        when(dietService.updateDiet(ArgumentMatchers.any(Diet.class))).thenReturn(diet);

        RequestBuilder request = MockMvcRequestBuilders.put("/api/diet/update")
                .contentType(APPLICATION_JSON_UTF8).content(jsonValue);
        MvcResult result = mvc.perform(request).andReturn();

        assertThat(result.getResponse().getContentAsString()).isEqualTo(expected);
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dietService,times(1)).updateDiet(ArgumentMatchers.any());
    }

    @Test
    void testMvcDeleteById() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.delete("/api/diet/delete/1");
        MvcResult result = mvc.perform(request).andReturn();
        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        verify(dietService,times(1)).deleteDietById(ArgumentMatchers.any());
    }
}
