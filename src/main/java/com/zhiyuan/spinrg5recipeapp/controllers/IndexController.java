package com.zhiyuan.spinrg5recipeapp.controllers;

import com.zhiyuan.spinrg5recipeapp.domain.Category;
import com.zhiyuan.spinrg5recipeapp.domain.UnitOfMeasure;
import com.zhiyuan.spinrg5recipeapp.repositories.CategoryRepository;
import com.zhiyuan.spinrg5recipeapp.repositories.UnitOfMeasureRepository;
import com.zhiyuan.spinrg5recipeapp.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {
//    private CategoryRepository categoryRepository;
//    private UnitOfMeasureRepository unitOfMeasureRepository;
//
//    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
//        this.categoryRepository = categoryRepository;
//        this.unitOfMeasureRepository = unitOfMeasureRepository;
//    }

    private  final RecipeService recipeService;


    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index"})
    public String getIndex(Model model){
//        System.out.println("Some message");
//        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
//        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
//
//        System.out.println("Cat Id is : " + categoryOptional.get().getId());
//        System.out.println("UOM Id is : " + unitOfMeasureOptional.get().getId());

        log.debug("Getting index page");

        model.addAttribute("recipes",recipeService.getRecipes());
        return "index";
    }
}
