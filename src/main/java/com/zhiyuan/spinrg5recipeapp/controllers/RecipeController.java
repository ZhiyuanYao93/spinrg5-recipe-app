package com.zhiyuan.spinrg5recipeapp.controllers;

import com.zhiyuan.spinrg5recipeapp.commands.RecipeCommand;
import com.zhiyuan.spinrg5recipeapp.exceptions.NotFoundException;
import com.zhiyuan.spinrg5recipeapp.services.RecipeService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeForm";

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }


    @GetMapping("/recipe/{id}/show")
    public String showId(@PathVariable(value = "id") String id, Model model){
            model.addAttribute("recipe",recipeService.findById(new Long(id)));
            return "recipe/show";
    }


    @GetMapping("/recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe",new RecipeCommand());
        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> {
                log.error(objectError.toString());
            });
            return RECIPE_RECIPEFORM_URL;
        }
        //TODO:solve the addition of category. Check recipeForm.html
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show" ;
    }


    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findRecipeCommandById(Long.valueOf(id)));
        return RECIPE_RECIPEFORM_URL;
    }


    @GetMapping("recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable String id){
        log.debug("Deleting recipe with id: " + id);

        recipeService.deleteById(Long.valueOf(id));

        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){
        log.error("Handling Not Found Error");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("404error");

        modelAndView.addObject("exception",exception);
        return modelAndView;

    }


//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(NumberFormatException.class)
//    public ModelAndView handleNumberFormat(Exception exception){
//        log.error("Handling  NumberFormat Error");
//        log.error(exception.getMessage());
//        ModelAndView modelAndView = new ModelAndView();
//
//        modelAndView.setViewName("400error");
//
//        modelAndView.addObject("exception",exception);
//        return modelAndView;
//
//    }


}
