package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.CategoryNotFoundException;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.repository.CategoryRepository;
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
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @AfterEach
    void tearDown() {
        //
    }

    @Test
    public void testFindAllCategories_ReturnsAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory("PIZZA"));
        categories.add(createCategory("PASTA"));

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> fetchedCategory = categoryService.findAllCategories();

        assertThat(fetchedCategory.get(0)).isSameAs(categories.get(0));
        verify(categoryRepository,times(1)).findAll();
    }

    @Test
    public void addCategory_ReturnsCategory() {
        Category category = createCategory("PIZZA");

        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);

        Category result = categoryService.addCategory(category);

        assertThat(result.getType()).isSameAs(category.getType());
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    public void updateCategory_ReturnsCategory() {
        Category category = createCategory("PIZZA");

        when(categoryRepository.save(ArgumentMatchers.any(Category.class))).thenReturn(category);

        Category result = categoryService.updateCategory(category);

        assertThat(result.getType()).isSameAs(category.getType());
        verify(categoryRepository,times(1)).save(category);
    }

    @Test
    public void deleteCategory() {
        categoryService.deleteCategoryById(1L);
        verify(categoryRepository,times(1)).deleteById(1L);
    }

    @Test
    void findCategoryById_ReturnsCategory() {
        Category category = createCategory("PIZZA");

        Optional<Category> optionalCategory = Optional.of(category);

        when(categoryRepository.findCategoryById(ArgumentMatchers.any(Long.class))).thenReturn(optionalCategory);

        Category fetchedCategory = categoryService.findCategoryById(1L);

        assertThat(fetchedCategory.getType()).isSameAs(category.getType());
        verify(categoryRepository, times(1)).findCategoryById(1L);
    }

    @Test
    void findCategoryById_DoesNotExistThrowsException() {
        Optional<Category> optionalCategory = Optional.empty();

        when(categoryRepository.findCategoryById(ArgumentMatchers.any(Long.class))).thenReturn(optionalCategory);

        Assertions.assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.findCategoryById(1L);
        });
    }

}