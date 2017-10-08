package com.zhiyuan.spinrg5recipeapp.services;


import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.converters.IngredientCommandToIngredient;
import com.zhiyuan.spinrg5recipeapp.converters.IngredientToIngredientCommand;
import com.zhiyuan.spinrg5recipeapp.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.zhiyuan.spinrg5recipeapp.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Ingredient;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;

import com.zhiyuan.spinrg5recipeapp.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService{
    private final IngredientToIngredientCommand ingredientToIngredientCommand;

    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    private final RecipeRepository recipeRepository;

    private final UnitOfMeasureRepository unitOfMeasureRepository;




    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository  = unitOfMeasureRepository;
    }




    @Override
    public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //TODO error handling
            log.error("Wanted recipe is not found" + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                                                                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                                                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
                                                                .findFirst();
        if (!ingredientCommandOptional.isPresent()){
            //TODO:error handling
            log.error("Wanted Ingredient is not found" + ingredientId);
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (! recipeOptional.isPresent()){
            //TODO error handling
            log.error("recipe with id {} not found ", ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }

        Recipe recipe = recipeOptional.get();

        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();

        if (ingredientOptional.isPresent()){
            Ingredient ingredientToSave = ingredientOptional.get();
            ingredientToSave.setAmount(ingredientCommand.getAmount());
            ingredientToSave.setDescription(ingredientCommand.getDescription());
            //TODO:find bug in the unitOfMeasureRepository way of implementation
            log.debug("Problem here");
            log.debug(String.valueOf(ingredientCommand.getUnitOfMeasure() == null));
            //ingredientToSave.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).orElseThrow(() -> new RuntimeException("UOM not found")));
            //ingredientToSave.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUnitOfMeasure().getId()).get());
            ingredientToSave.setUom(unitOfMeasureRepository
                    .findById(ingredientCommand.getUnitOfMeasure()
                            .getId())
                    .orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));


        }else{
            recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);

        //TODO: check for save failure

        return ingredientToIngredientCommand.convert(savedRecipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst()
                .get());
    }
}
