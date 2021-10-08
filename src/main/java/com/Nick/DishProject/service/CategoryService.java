package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.CategoryNotFoundException;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Diet by id "+id+" was not found."));
    }

    public Category addCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void DeleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public List<Category> findAllCategories() {
        return StreamSupport
                .stream(categoryRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}
