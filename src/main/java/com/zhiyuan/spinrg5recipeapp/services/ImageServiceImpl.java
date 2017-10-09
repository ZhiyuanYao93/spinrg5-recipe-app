package com.zhiyuan.spinrg5recipeapp.services;

import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }



    @Override
    public void saveImage(Long recipeId, MultipartFile multipartFile) {
            log.debug("Received a file");
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            log.error("Recipe does exists from DB");
            throw new RuntimeException("Recipe not existing with ID: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        try {
            Byte[] imageBytes = new Byte[multipartFile.getBytes().length];

            int i = 0;

            for (Byte singleByte : multipartFile.getBytes()){
                imageBytes[i++] = singleByte;
            }

            recipe.setImages(imageBytes);

            recipeRepository.save(recipe);


        } catch (IOException e) {
            log.error("IO Excption of multiPartfile.getBytes()",e);

            //TODO: to revise before production
            e.printStackTrace();
        }





    }
}
