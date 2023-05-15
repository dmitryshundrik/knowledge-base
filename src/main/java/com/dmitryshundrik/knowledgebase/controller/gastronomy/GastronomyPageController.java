package com.dmitryshundrik.knowledgebase.controller.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.Article;
import com.dmitryshundrik.knowledgebase.model.common.dto.ArticleDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.model.gastronomy.dto.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.service.common.ArticleService;
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

    @Autowired
    private ArticleService articleService;

    @GetMapping()
    public String getGastronomyPage(Model model) {
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeService.getAll());
        List<CocktailViewDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailService.getAll());
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

    @GetMapping("/recipe/{recipeSlug}")
    public String getRecipe(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeViewDTO recipeViewDTO = recipeService.getRecipeViewDTO(bySlug);
        model.addAttribute("recipe", recipeViewDTO);
        return "gastronomy/recipe";
    }

    @GetMapping("/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAll();
        List<CocktailViewDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailList);
        model.addAttribute("cocktails", cocktailDTOList);
        return "gastronomy/cocktail-all";
    }

    @GetMapping("/cocktail/{cocktailSlug}")
    public String getCocktail(@PathVariable String cocktailSlug, Model model) {
        Cocktail bySlug = cocktailService.getBySlug(cocktailSlug);
        CocktailViewDTO cocktailViewDTO = cocktailService.getCocktailViewDTO(bySlug);
        model.addAttribute("cocktail", cocktailViewDTO);
        return "gastronomy/cocktail";
    }

    @GetMapping("/article/{articleId}")
    public String getArticle(@PathVariable String articleId, Model model) {
        Article byId = articleService.getById(articleId);
        ArticleDTO articleDTO = articleService.getArticleDTO(byId);
        model.addAttribute("article", articleDTO);
        return "gastronomy/article";
    }

    @GetMapping("/country/{slug}")
    public String getRecipesByCountry(@PathVariable String slug, Model model) {
        List<Recipe> allByCountry = recipeService.getAllByCountry(Country.valueOf(slug.toUpperCase()));
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(allByCountry);
        model.addAttribute("country", Country.valueOf(slug.toUpperCase()));
        model.addAttribute("recipes", recipeViewDTOList);
        return "gastronomy/recipe-all-by-country";
    }

}
