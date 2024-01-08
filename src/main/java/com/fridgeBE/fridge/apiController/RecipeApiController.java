package com.fridgeBE.fridge.apiController;

import com.fridgeBE.fridge.dto.ResponseDto;
import com.fridgeBE.fridge.model.Recipe;
import com.fridgeBE.fridge.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecipeApiController {

    @Autowired
    RecipeService recipeService;

    @GetMapping("/recipe")
    public ResponseDto getRecipe() {
        List<Recipe> recipe = recipeService.getRecipe();

        return new ResponseDto(HttpStatus.OK.value(), recipe);
    }

}