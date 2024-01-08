package com.fridgeBE.fridge.service;

import com.fridgeBE.fridge.model.Recipe;
import com.fridgeBE.fridge.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Transactional
    public List<Recipe> getRecipe() {
        return recipeRepository.findAll();
    }
}