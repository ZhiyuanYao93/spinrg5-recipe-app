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
            //add new ingredient
            Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        //TODO: check for save failure

        Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                .findFirst();


        if (!savedIngredientOptional.isPresent()){
            //unsafe here. May not precise.
             savedIngredientOptional = savedRecipe.getIngredients().stream()
                     .filter(ingredient -> ingredient.getDescription().equals(ingredientCommand.getDescription()))
                     .filter(ingredient -> ingredient.getAmount().equals(ingredientCommand.getAmount()))
                     .filter(ingredient -> ingredient.getUom().getId().equals(ingredientCommand.getUnitOfMeasure().getId()))
                     .findFirst();
        }

//        return ingredientToIngredientCommand.convert(savedRecipe
//                .getIngredients()
//                .stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
//                .findFirst()
//                .get());
        return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
    }

    @Transactional
    @Override
    public void deleteById(Long recipeId, Long ingredientId) {
        log.debug("Prepare to delete ingredient with id: " + ingredientId + " from recipe of ID: " + recipeId);

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (! recipeOptional.isPresent()){
            log.error("Recipe with ID: " + recipeId + " cannot be found.");
            throw  new RuntimeException("Recipe with required ID is not found. ID: " + recipeId);
        }
        log.debug("Found target recipe");
        Recipe recipe = recipeOptional.get();



        Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

        if (!ingredientOptional.isPresent()){
            log.error("ingredient with required id: " + recipeId + " cannot be found");

            throw new RuntimeException("ingredient with required id: " + recipeId + " cannot be found");
        }
        log.debug("Found target ingredient");
        Ingredient ingredient = ingredientOptional.get();

        ingredient.setRecipe(null);
        recipe.getIngredients().remove(ingredient);


        recipeRepository.save(recipe);

    }
}
