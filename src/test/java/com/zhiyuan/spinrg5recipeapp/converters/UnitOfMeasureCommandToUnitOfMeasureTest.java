package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.UnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
    private static final Long ID_VALUE = new Long(1L);
    private static final String DESCR = new String("Description");

    UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    @Before
    public void setUp() throws Exception {
        this.unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(unitOfMeasureCommandToUnitOfMeasure.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID_VALUE);
        unitOfMeasureCommand.setDescription(DESCR);

        //when
        UnitOfMeasure unitOfMeasure = unitOfMeasureCommandToUnitOfMeasure.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(unitOfMeasure.getId(), ID_VALUE);
        assertEquals(unitOfMeasure.getDescription(),DESCR);
    }

}