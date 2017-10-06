package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.CategoryCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Category;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryCommandToCategoryTest {
    public static final Long LONG_VALUE = new Long(1L);
    public static final String DESCR = new String("description");
    CategoryCommandToCategory categoryCommandToCategory;

    @Before
    public void setUp() throws Exception {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(LONG_VALUE);
        categoryCommand.setDescription(DESCR);

        //when
        Category category = categoryCommandToCategory.convert(categoryCommand);


        //then
        //cast to Long in order to avoid ambiguous call
        assertEquals((Long) LONG_VALUE,(Long) category.getId());
        assertEquals(DESCR,category.getDescription());


    }

}