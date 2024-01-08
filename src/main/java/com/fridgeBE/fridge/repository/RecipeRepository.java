package com.fridgeBE.fridge.repository;

import com.fridgeBE.fridge.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    List<Recipe> findAll();
}