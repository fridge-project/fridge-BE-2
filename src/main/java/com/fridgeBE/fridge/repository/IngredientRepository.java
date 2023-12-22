package com.fridgeBE.fridge.repository;

import com.fridgeBE.fridge.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
    Ingredient findByName(String name);
}
