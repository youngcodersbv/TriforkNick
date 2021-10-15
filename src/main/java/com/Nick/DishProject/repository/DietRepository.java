package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Diet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DietRepository extends CrudRepository<Diet, Long> {
    Optional<Diet> findDietById(Long id);

}
