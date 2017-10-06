package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.NotesCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesToNotesCommandTest {
    private static final Long LONG_VALUE = new Long(1L);
    private static final String RECIPE_NOTES = new String("Recipe Notes");

    NotesToNotesCommand notesToNotesCommand;

    @Before
    public void setUp() throws Exception {
        this.notesToNotesCommand = new NotesToNotesCommand();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(notesToNotesCommand.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(notesToNotesCommand.convert(new Notes()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Notes notes = new Notes();
        notes.setId(LONG_VALUE);
        notes.setRecipeNotes(RECIPE_NOTES);
        //when
        NotesCommand notesCommand = notesToNotesCommand.convert(notes);


        //then
        assertNotNull(notesCommand);
        assertEquals(notesCommand.getId(),LONG_VALUE);
        assertEquals(notesCommand.getRecipeNotes(),RECIPE_NOTES);
    }
}