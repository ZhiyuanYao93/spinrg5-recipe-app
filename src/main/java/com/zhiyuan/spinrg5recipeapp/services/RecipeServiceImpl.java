package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.converters.RecipeCommandToRecipe;
import com.zhiyuan.spinrg5recipeapp.converters.RecipeToRecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("I am in the ServiceImpl");
        Set<Recipe> recipeSet = new HashSet<>();
        recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long l) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(l);

        if (!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe not fuound");
        }

        return recipeOptional.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        Recipe savedRecipe = recipeRepository.save(recipe);
        log.debug("savedRecipe Id: {}",savedRecipe.getId());

        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findRecipeCommandById(long l) {
        return recipeToRecipeCommand.convert(findById(l));
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
