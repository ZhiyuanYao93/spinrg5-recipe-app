package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    ImageService imageService;




    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.imageService = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void saveImage() throws Exception {
        //given
        Long id = 1L;
        MultipartFile file = new MockMultipartFile("imagefile","testing.txt","text/plain","Spring Framework".getBytes());

        Recipe recipe = new Recipe();
        recipe.setId(id);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        //when
        imageService.saveImage(id,file);



        //then
        verify(recipeRepository,times(1)).save(argumentCaptor.capture());

        Recipe savedRecipe = argumentCaptor.getValue();

        assertEquals(file.getBytes().length,savedRecipe.getImages().length);


    }

}