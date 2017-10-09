package com.zhiyuan.spinrg5recipeapp.controllers;

import com.zhiyuan.spinrg5recipeapp.commands.IngredientCommand;
import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.commands.UnitOfMeasureCommand;
import com.zhiyuan.spinrg5recipeapp.services.IngredientService;
import com.zhiyuan.spinrg5recipeapp.services.RecipeService;
import com.zhiyuan.spinrg5recipeapp.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {
    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }


    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(@PathVariable String recipeId, Model model){
        log.debug("Getting ingredient list for recipe with ID: " + recipeId);

        model.addAttribute("recipe",recipeService.findRecipeCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }


    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(@PathVariable(name = "recipeId") String recipeId,
                                 @PathVariable(name = "ingredientId") String ingredientId,
                                 Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));

        return "recipe/ingredient/show";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable(name = "recipeId") String recipeId,
                                   @PathVariable(name = "ingredientId") String ingredientId,
                                   Model model){
        model.addAttribute("ingredient",ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId),Long.valueOf(ingredientId)));
        model.addAttribute("uomList",unitOfMeasureService.listAllUom());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        IngredientCommand savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug(String.valueOf(savedIngredientCommand == null));

        log.debug("Saved recipe id: " + savedIngredientCommand.getRecipeId());
        log.debug("Saved ingredient id: " + savedIngredientCommand.getId() );

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show" ;
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createIngredient(@PathVariable(name = "recipeId") String recipeId,Model model){
        RecipeCommand recipeCommand = recipeService.findRecipeCommandById(Long.valueOf(recipeId));
        //TODO:Exception if null

        //create ingredientComman
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipeId));

        //init UOM list for the newly created ingredientCommand
        ingredientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());


        model.addAttribute("ingredient",ingredientCommand);
        model.addAttribute("uomList",unitOfMeasureService.listAllUom());

        return "recipe/ingredient/ingredientForm";

    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable(name = "recipeId") String recipeId,
                                   @PathVariable(name = "ingredientId") String ingredientId){
        log.debug("deleting ingredient with id: " + ingredientId + "of recipe of Id: " + recipeId );

        ingredientService.deleteById(Long.valueOf(recipeId),Long.valueOf(ingredientId));

        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

}

