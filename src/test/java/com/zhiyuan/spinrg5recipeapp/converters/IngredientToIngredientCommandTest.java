package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Ingredient;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientToIngredientCommandTest {
    private static final Long LONG_VALUE = new Long(1L);
    private static final Long UOM_ID = new Long(2L);
    private static final String DESCR = new String("Description");
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Recipe RECIPE = new Recipe();


   IngredientToIngredientCommand ingredientToIngredientCommand;

    @Before
    public void setUp() throws Exception {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(ingredientToIngredientCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
    }



    @Test
    public void convertWithUom() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();

        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(UOM_ID);
        unitOfMeasure.setDescription(DESCR);

        ingredient.setId(LONG_VALUE);
        ingredient.setDescription(DESCR);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);
        ingredient.setUom(unitOfMeasure);

        //when

        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);


        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ingredientCommand.getId(),LONG_VALUE);
        assertEquals(ingredientCommand.getDescription(),DESCR);
        assertEquals(ingredientCommand.getAmount(),AMOUNT);

        assertEquals(ingredientCommand.getUnitOfMeasure().getId(),UOM_ID);
        assertEquals(ingredientCommand.getUnitOfMeasure().getDescription(),DESCR);
    }

    @Test
    public void convertWithOutUom() throws Exception {
        //given
        Ingredient ingredient = new Ingredient();

        ingredient.setId(LONG_VALUE);
        ingredient.setDescription(DESCR);
        ingredient.setAmount(AMOUNT);
        ingredient.setRecipe(RECIPE);

        //when
        IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUnitOfMeasure());

        assertEquals(ingredientCommand.getId(),LONG_VALUE);
        assertEquals(ingredientCommand.getDescription(),DESCR);
        assertEquals(ingredientCommand.getAmount(),AMOUNT);

    }
}