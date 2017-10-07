package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();

    Recipe findById(Long l);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findRecipeCommandById(long l);

    void deleteById(Long id);
}
