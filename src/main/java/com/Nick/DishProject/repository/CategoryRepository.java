package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findCategoryById(Long id);
}
