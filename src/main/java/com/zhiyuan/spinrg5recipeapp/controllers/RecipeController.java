package com.zhiyuan.spinrg5recipeapp.controllers;

import com.zhiyuan.spinrg5recipeapp.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RecipeController {
    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/show/{id}")
    public String showId(@PathVariable(value = "id") String id, Model model){
            model.addAttribute("recipe",recipeService.findById(new Long(id)));
            return "recipe/show";
    }
}
