package com.Nick.DishProject.repository;

import com.Nick.DishProject.model.Diet;
import org.springframework.data.repository.CrudRepository;

public interface DietRepository extends CrudRepository<Diet, Long> {

Diet findById(long id);

}
