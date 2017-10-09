package com.zhiyuan.spinrg5recipeapp.converters;


import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {
    //TODO: test Byte[] images conversion
    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCR = new String("Description");
    private static final Integer PREP_TIME = new Integer(2);
    private static final Integer COOK_TIME = new Integer(3);
    private static final Integer SERVINGS = new Integer(4);
    private static final String SOURCE = new String("Source");
    private static final String URL = new String("Url");
    private static final String DIRECTIONS = new String("Directions");

    private static final Difficulty DIFFICULTY = Difficulty.MEDIUM;

    private static final Long CAT1_ID = new Long(5L);
    private static final Long CAT2_ID = new Long(6L);

    private static final Long ING1_ID = new Long(7L);
    private static final Long ING2_ID = new Long(8L);

    private static final Long NOTES_ID = new Long(9L);

    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() throws Exception {
        this.recipeToRecipeCommand = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new CategoryToCategoryCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(recipeToRecipeCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(recipeToRecipeCommand.convert(new Recipe()));
    }



    @Test
    public void convert() throws Exception {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setDescription(DESCR);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);

        recipe.setDifficulty(DIFFICULTY);

        Category category1 = new Category();
        category1.setId(CAT1_ID);

        Category category2 = new Category();
        category2.setId(CAT2_ID);

        recipe.getCategories().add(category1);
        recipe.getCategories().add(category2);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(ING1_ID);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ING2_ID);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID);
        recipe.setNotes(notes);

        //when
        RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);

        //then
        assertNotNull(recipeCommand);
        assertEquals(ID_VALUE, recipeCommand.getId());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(DESCR, recipeCommand.getDescription());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(NOTES_ID, recipeCommand.getNotes().getId());
        assertEquals(2, recipeCommand.getCategories().size());
        assertEquals(2, recipeCommand.getIngredients().size());

    }

}