package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.CategoryNotFoundException;
import com.Nick.DishProject.exception.DietNotFoundException;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.repository.DietRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.Nick.DishProject.testUtil.ObjectInstantiationUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DietServiceTest {

    @Mock
    private DietRepository dietRepository;

    @InjectMocks
    private DietService dietService;

    @Test
    public void testFindAllDiets_ReturnsAllDiets() {
        List<Diet> diets = new ArrayList<>();
        diets.add(createDiet("KETO"));
        diets.add(createDiet("REGULAR"));

        when(dietRepository.findAll()).thenReturn(diets);

        List<Diet> fetchedDiet = dietService.findAllDiets();

        assertThat(fetchedDiet.get(0)).isSameAs(diets.get(0));
        verify(dietRepository,times(1)).findAll();
    }

    @Test
    public void addDiet_ReturnsDiet() {
        Diet diet = createDiet("KETO");

        when(dietRepository.save(ArgumentMatchers.any(Diet.class))).thenReturn(diet);

        Diet result = dietService.addDiet(diet);

        assertThat(result.getType()).isSameAs(diet.getType());
        verify(dietRepository,times(1)).save(diet);
    }

    @Test
    public void updateDiet_ReturnsDiet() {
        Diet diet = createDiet("KETO");

        when(dietRepository.save(ArgumentMatchers.any(Diet.class))).thenReturn(diet);

        Diet result = dietService.updateDiet(diet);

        assertThat(result.getType()).isSameAs(diet.getType());
        verify(dietRepository,times(1)).save(diet);
    }

    @Test
    public void deleteDiet() {
        dietService.deleteDietById(1L);
        verify(dietRepository,times(1)).deleteById(1L);
    }

    @Test
    void findDietById_ReturnsDiet() {
        Diet diet = createDiet("KETO");

        Optional<Diet> optionalDiet = Optional.of(diet);

        when(dietRepository.findDietById(ArgumentMatchers.any(Long.class))).thenReturn(optionalDiet);

        Diet fetchedDiet = dietService.findDietById(1L);

        assertThat(fetchedDiet.getType()).isSameAs(diet.getType());
        verify(dietRepository, times(1)).findDietById(1L);
    }

    @Test
    void findDietById_DoesNotExistThrowsException() {
        Optional<Diet> optionalDiet = Optional.empty();

        when(dietRepository.findDietById(ArgumentMatchers.any(Long.class))).thenReturn(optionalDiet);

        Assertions.assertThrows(DietNotFoundException.class, () -> {
            dietService.findDietById(1L);
        });
    }

}