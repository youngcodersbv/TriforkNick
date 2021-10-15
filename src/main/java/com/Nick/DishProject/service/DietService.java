package com.Nick.DishProject.service;

import com.Nick.DishProject.exception.DietNotFoundException;
import com.Nick.DishProject.model.Diet;
import com.Nick.DishProject.repository.DietRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DietService {

    DietRepository dietRepository;

    @Autowired
    public DietService(DietRepository dietRepository) {
        this.dietRepository = dietRepository;
    }

    public Diet findDietById(Long id) {
        return dietRepository.findDietById(id).orElseThrow(() -> new DietNotFoundException("Diet by id "+id+" was not found."));
    }

    public Diet addDiet(Diet diet) {
        return dietRepository.save(diet);
    }

    public Diet updateDiet(Diet diet) {
        return dietRepository.save(diet);
    }

    public void deleteDietById(Long id) {
        dietRepository.deleteById(id);
    }

    public List<Diet> findAllDiets() {
        return StreamSupport
                .stream(dietRepository.findAll().spliterator(),false)
                .collect(Collectors.toList());
    }
}
