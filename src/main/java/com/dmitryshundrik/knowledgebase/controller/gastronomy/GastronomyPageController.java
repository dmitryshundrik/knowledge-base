package com.dmitryshundrik.knowledgebase.controller.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gastronomy")
public class GastronomyPageController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CocktailService cocktailService;

    @GetMapping()
    public String getGastronomyPage(Model model) {
        model.addAttribute("recipes", recipeService.getAll());
        model.addAttribute("cocktails", cocktailService.getAll());
        model.addAttribute("countries", Country.values());
        return "gastronomy/gastronomy-page";
    }

    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAll());
        return "gastronomy/recipe-all";
    }

    @GetMapping("/recipe/{slug}")
    public String getRecipe(@PathVariable String slug, Model model) {

        return "gastronomy/recipe";
    }

    @GetMapping("/cocktail/all")
    public String getAllCocktails(Model model) {
        model.addAttribute("cocktails", recipeService.getAll());
        return "gastronomy/cocktail-all";
    }
}
