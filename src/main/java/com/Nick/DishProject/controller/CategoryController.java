package com.Nick.DishProject.controller;

import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category newCategory = categoryService.addCategory(category);
        return new ResponseEntity<>(newCategory,HttpStatus.CREATED);
    }

    @PutMapping("/update")
    @Transactional
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        Category newCategory = categoryService.updateCategory(category);
        return new ResponseEntity<>(newCategory, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Transactional
    public ResponseEntity<?> deleteCategoryById(@PathVariable("id")Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
