package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.converters.RecipeCommandToRecipe;
import com.zhiyuan.spinrg5recipeapp.converters.RecipeToRecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {
    private static final String TEST_DESCR = new String("Test Description");

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    RecipeCommandToRecipe recipeCommandToRecipe;


//Test class should have exactly one public zero-argument constructor
//    @Autowired
//    public RecipeServiceIT(RecipeService recipeService, RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand, RecipeCommandToRecipe recipeCommandToRecipe) {
//        this.recipeService = recipeService;
//        this.recipeRepository = recipeRepository;
//        this.recipeToRecipeCommand = recipeToRecipeCommand;
//        this.recipeCommandToRecipe = recipeCommandToRecipe;
//    }

    @Test
    @Transactional
    public void testSaveDescription() throws Exception {
        //given
        Iterable<Recipe> recipes = recipeRepository.findAll();

        Recipe testRecipe = recipes.iterator().next();

        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(testRecipe);

        //when
        recipeCommand.setDescription(TEST_DESCR);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertEquals(TEST_DESCR,savedRecipeCommand.getDescription());
        assertEquals(testRecipe.getId(),savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(),savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(),savedRecipeCommand.getIngredients().size());
    }
}
