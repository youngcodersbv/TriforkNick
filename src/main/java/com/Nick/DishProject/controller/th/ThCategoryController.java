package com.Nick.DishProject.controller.th;

import com.Nick.DishProject.controller.CategoryController;
import com.Nick.DishProject.model.Category;
import com.Nick.DishProject.service.CategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Category> categories = categoryService.findAllCategories();
        List<Category> sortedList = categories.stream()
                .sorted((Comparator.comparing(Category::getId)))
                .collect(Collectors.toList());

        model.addAttribute("categories",(Iterable<Category>) sortedList);
        if(model.getAttribute("category") == null) {
            model.addAttribute("category", new Category());
        }
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
    public String postNewCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("category",category);
            return categoryIndex(model);
        }
        categoryController.addCategory(category);
        return "redirect:/categories";
    }

    @PutMapping("/categories")
    public String updateCategory(@Valid @ModelAttribute Category category, BindingResult result, Model model) {
        if(result.hasErrors()) {
            model.addAttribute("category",category);
            return categoryOverview();
        }
        categoryController.updateCategory(category);
        return "redirect:/categories";
    }

    @DeleteMapping("/categories")
    public String deleteCategory(@ModelAttribute Category category) {
        categoryController.deleteCategoryById(category.getId());
        return "redirect:/categories";
    }
}
