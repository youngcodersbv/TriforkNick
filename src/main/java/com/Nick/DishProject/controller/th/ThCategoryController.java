package com.Nick.DishProject.controller.th;

import com.Nick.DishProject.controller.CategoryController;
import com.Nick.DishProject.exception.CategoryNotFoundException;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.Optional;

@Controller
public class ThCategoryController {

    private CategoryService categoryService;
    private CategoryController categoryController;

    public ThCategoryController(CategoryService categoryService, CategoryController categoryController) {
        this.categoryController=categoryController;
        this.categoryService=categoryService;
    }

    @GetMapping("/categories")
    public String categoryIndex(Model model) {
        Iterable iter = categoryService.findAllCategories();
        model.addAttribute("categories",iter);
        return "categories";
    }

    @GetMapping("categories/{id}")
    public String categorySpecific(@PathVariable("id")Long id, RedirectAttributes redirAttr) {
        Category category = categoryService.findCategoryById(id);
        redirAttr.addFlashAttribute("category", category);
        return "redirect:/category";
    }



    @GetMapping("/category")
    public String categoryOverview() {
        return "/category";
    }

    @PostMapping("/categories")
    public String postNewCategory(@ModelAttribute Category category) {
        if(category.getId() != null) {
            Category pCategory = categoryService.findCategoryById(category.getId());
            pCategory.setType(category.getType());
            categoryController.updateCategory(pCategory);
            return "redirect:/categories";
        }
        categoryController.addCategory(category);
        return "redirect:/categories";
    }

    @PutMapping("/categories")
    public String updateCategory(@ModelAttribute Category category) {
        categoryController.updateCategory(category);
        return "redirect:/categories";
    }
}
