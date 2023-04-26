package com.dmitryshundrik.knowledgebase.controller.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/gastronomy")
public class GastronomyPageController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private CocktailService cocktailService;

    @GetMapping()
    public String getGastronomyPage(Model model) {
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeService.getAll());
        List<CocktailDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailService.getAll());
        model.addAttribute("recipes", recipeViewDTOList);
        model.addAttribute("cocktails", cocktailDTOList);
        model.addAttribute("countries", Country.values());
        return "gastronomy/gastronomy-page";
    }

    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAll();
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeList);
        model.addAttribute("recipes", recipeViewDTOList);
        return "gastronomy/recipe-all";
    }

    @GetMapping("/recipe/{slug}")
    public String getRecipe(@PathVariable String slug, Model model) {
        Recipe bySlug = recipeService.getBySlug(slug);
        RecipeViewDTO recipeViewDTO = recipeService.getRecipeViewDTO(bySlug);
        model.addAttribute("recipe", recipeViewDTO);
        return "gastronomy/recipe";
    }

    @GetMapping("/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAll();
        List<CocktailDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailList);
        model.addAttribute("cocktails", cocktailDTOList);
        return "gastronomy/cocktail-all";
    }

    @GetMapping("/cocktail/{slug}")
    public String getCocktail(@PathVariable String slug, Model model) {
        Cocktail bySlug = cocktailService.getBySlug(slug);
        CocktailDTO cocktailDTO = cocktailService.getCocktailViewDTO(bySlug);
        model.addAttribute("cocktail", cocktailDTO);
        return "gastronomy/recipe";
    }

}
