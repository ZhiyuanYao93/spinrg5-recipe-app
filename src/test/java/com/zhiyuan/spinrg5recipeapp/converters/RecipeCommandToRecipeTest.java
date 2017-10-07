package com.zhiyuan.spinrg5recipeapp.converters;

import com.sun.org.apache.bcel.internal.generic.LNEG;
import com.zhiyuan.spinrg5recipeapp.commands.CategoryCommand;
import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.commands.NotesCommand;
import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Difficulty;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class RecipeCommandToRecipeTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCR = new String("Description");
    private static final Integer PREP_TIME = new Integer(2);
    private static final Integer COOK_TIME = new Integer(3);
    private static final Integer SERVINGS = new Integer(4);
    private static final String SOURCE = new String("Source");
    private static final String URL = new String("Url");
    private static final String DIRECTIONS = new String("Directions");

    private static final Difficulty DIFFICULTY = Difficulty.DIFFICULT;

    private static final Long CAT1_ID = new Long(5L);
    private static final Long CAT2_ID = new Long(6L);

    private static final Long ING1_ID = new Long(7L);
    private static final Long ING2_ID = new Long(8L);

    private static final Long NOTES_ID = new Long(9L);

    RecipeCommandToRecipe recipeCommandToRecipe;

    @Before
    public void setUp() throws Exception {
        this.recipeCommandToRecipe = new RecipeCommandToRecipe(new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(recipeCommandToRecipe.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(recipeCommandToRecipe.convert(new RecipeCommand()));
    }


    @Test
    public void convert() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VALUE);
        recipeCommand.setDescription(DESCR);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDirections(DIRECTIONS);

        recipeCommand.setDifficulty(DIFFICULTY);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT1_ID);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT2_ID);

        recipeCommand.getCategories().add(categoryCommand1);
        recipeCommand.getCategories().add(categoryCommand2);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(ING1_ID);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(ING2_ID);

        recipeCommand.getIngredients().add(ingredientCommand1);
        recipeCommand.getIngredients().add(ingredientCommand2);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);


        //when
        Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

        //then
        assertNotNull(recipe);
        assertEquals(ID_VALUE, recipe.getId());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(DESCR, recipe.getDescription());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(URL, recipe.getUrl());
        assertEquals(NOTES_ID, recipe.getNotes().getId());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());

    }

}