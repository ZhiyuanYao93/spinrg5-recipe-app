package com.zhiyuan.spinrg5recipeapp.converters;

import com.zhiyuan.spinrg5recipeapp.commands.NotesCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Notes;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NotesCommandToNotesTest {
    private static final Long LONG_VALUE = new Long(1L);
    private static final String RECIPE_NOTES = new String("Recipe Notes");

    NotesCommandToNotes notesCommandToNotes;

    @Before
    public void setUp() throws Exception {
        this.notesCommandToNotes = new NotesCommandToNotes();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(notesCommandToNotes.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
    }


    @Test
    public void convert() throws Exception {
        //given
        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(LONG_VALUE);
        notesCommand.setRecipeNotes(RECIPE_NOTES);

        //when
        Notes notes = notesCommandToNotes.convert(notesCommand);


        //then
        assertNotNull(notes);
        assertEquals(notes.getId(),LONG_VALUE);
        assertEquals(notes.getRecipeNotes(),RECIPE_NOTES);
    }

}