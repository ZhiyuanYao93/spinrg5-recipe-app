package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    void deleteById(Long recipeId, Long ingredientId);
}
