package com.zhiyuan.spinrg5recipeapp.controllers;

import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.domain.Recipe;
import com.zhiyuan.spinrg5recipeapp.services.ImageService;
import com.zhiyuan.spinrg5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Controller
public class ImageController {
    private final ImageService imageService;
    private final RecipeService recipeService;

    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @PostMapping("recipe/{id}/image")
    public String handleImagePost(@PathVariable String id, @RequestParam("imagefile")MultipartFile file){

        imageService.saveImage(Long.valueOf(id),file);

        return "redirect:/recipe/" + id + "/show";

    }

    @GetMapping("recipe/{id}/image")
    public String showUploadForm(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findRecipeCommandById(Long.valueOf(id)));

        return "/recipe/imageUploadForm";
    }

    @GetMapping("recipe/{id}/recipeimage")
    public void retrieveImageFromDB(@PathVariable(name = "id") String recipeId, HttpServletResponse httpServletResponse) throws IOException {
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));

        //TODO:handle exception better in production
        if (recipeCommand.getImages() == null){
            log.error("No image stored for this recipe with id: " + recipeId);
            throw new RuntimeException("No image stored for this recipe with id: " + recipeId);
        }

        byte[] imageBytes = new byte[recipeCommand.getImages().length];

        int i = 0;

        for (Byte singleByte : recipeCommand.getImages()){
            //Auto-unboxing happens here: from Byte --> byte
            imageBytes[i++] = singleByte;
        }

        httpServletResponse.setContentType("image/jpeg");
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        IOUtils.copy(inputStream,httpServletResponse.getOutputStream());
    }
}
