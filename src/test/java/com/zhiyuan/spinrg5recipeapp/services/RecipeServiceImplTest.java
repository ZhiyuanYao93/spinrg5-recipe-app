package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.converters.RecipeCommandToRecipe;
import com.zhiyuan.spinrg5recipeapp.converters.RecipeToRecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.exceptions.NotFoundException;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository,recipeToRecipeCommand,recipeCommandToRecipe);
    }

    @Test
    public void getRecipeByIdTest() throws Exception{
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);

        assertNotNull("Null recipe returned",recipeReturned);

        verify(recipeRepository,times(1)).findById(anyLong());

        verify(recipeRepository,never()).findAll();

    }

    @Test
    public void getRecipesTest() throws Exception {
        Recipe recipe = new Recipe();

        HashSet recipeData = new HashSet();

        recipeData.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipeData);

        Set<Recipe> recipeSet = recipeService.getRecipes();

        assertEquals(recipeSet.size(),1);

        verify(recipeRepository,times(1)).findAll();
        verify(recipeRepository,never()).findById(anyLong());
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        Long id = new Long(1L);

        recipeService.deleteById(id);

        verify(recipeRepository,times(1)).deleteById(anyLong());
    }

    @Test(expected = NotFoundException.class)
    public void testFindByIdNotFound() throws Exception {
        Optional<Recipe> recipeOptional = Optional.empty();

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        Recipe recipeReturned = recipeService.findById(1L);


    }
}