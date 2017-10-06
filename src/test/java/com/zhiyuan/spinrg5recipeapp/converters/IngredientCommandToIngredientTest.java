package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.commands.UnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Ingredient;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngredientCommandToIngredientTest {
    private static final Recipe recipe = new Recipe();
    private static final Long LONG_VALUE = new Long(1L);
    private static final String DESCR = new String("Description");
    private static final BigDecimal AMOUNT = new BigDecimal("1");
    private static final Long UOM_ID = new Long(2L);

    IngredientCommandToIngredient ingredientCommandToIngredient;

    @Before
    public void setUp() throws Exception {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(ingredientCommandToIngredient.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
       assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
    }



    @Test
    public void convert() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();

        unitOfMeasureCommand.setId(UOM_ID);
        unitOfMeasureCommand.setDescription(DESCR);

        ingredientCommand.setId(LONG_VALUE);
        ingredientCommand.setDescription(DESCR);
        ingredientCommand.setAmount(AMOUNT);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        //when

        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);


        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ingredient.getId(),LONG_VALUE);
        assertEquals(ingredient.getDescription(),DESCR);
        assertEquals(ingredient.getAmount(),AMOUNT);
        assertEquals(ingredient.getUom().getId(), UOM_ID);
        assertEquals(ingredient.getUom().getDescription(),DESCR);
    }

    @Test
    public void convertWithNullUom() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(LONG_VALUE);
        ingredientCommand.setDescription(DESCR);
        ingredientCommand.setAmount(AMOUNT);

        //when
        Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);


        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());

        assertEquals(ingredient.getId(),LONG_VALUE);
        assertEquals(ingredient.getDescription(),DESCR);
        assertEquals(ingredient.getAmount(),AMOUNT);

    }
}