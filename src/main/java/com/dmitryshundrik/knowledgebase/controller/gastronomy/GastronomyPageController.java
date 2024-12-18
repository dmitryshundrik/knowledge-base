package com.dmitryshundrik.knowledgebase.controller.gastronomy;

import com.dmitryshundrik.knowledgebase.entity.common.Article;
import com.dmitryshundrik.knowledgebase.dto.common.ArticleDTO;
import com.dmitryshundrik.knowledgebase.util.enums.Country;
import com.dmitryshundrik.knowledgebase.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.CocktailViewDTO;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.RecipeViewDTO;
import com.dmitryshundrik.knowledgebase.service.common.ArticleService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.ARTICLE;
import static com.dmitryshundrik.knowledgebase.util.Constants.COCKTAIL;
import static com.dmitryshundrik.knowledgebase.util.Constants.COCKTAIL_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.COUNTRY;
import static com.dmitryshundrik.knowledgebase.util.Constants.COUNTRY_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RECIPE;
import static com.dmitryshundrik.knowledgebase.util.Constants.RECIPE_LIST;

@Controller
@RequestMapping("/gastronomy")
@RequiredArgsConstructor
public class GastronomyPageController {

    private final RecipeService recipeService;

    private final CocktailService cocktailService;

    private final ArticleService articleService;

    @GetMapping
    public String getGastronomyPage(Model model) {
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeService.getAll());
        List<CocktailViewDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailService.getAll());
        model.addAttribute(RECIPE_LIST, recipeViewDTOList);
        model.addAttribute(COCKTAIL_LIST, cocktailDTOList);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "gastronomy/gastronomy-page";
    }

    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAll();
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(recipeList);
        model.addAttribute(RECIPE_LIST, recipeViewDTOList);
        return "gastronomy/recipe-all";
    }

    @GetMapping("/recipe/{recipeSlug}")
    public String getRecipe(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeViewDTO recipeViewDTO = recipeService.getRecipeViewDTO(bySlug);
        model.addAttribute(RECIPE, recipeViewDTO);
        return "gastronomy/recipe";
    }

    @GetMapping("/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAll();
        List<CocktailViewDTO> cocktailDTOList = cocktailService.getCocktailViewDTOList(cocktailList);
        model.addAttribute(COCKTAIL_LIST, cocktailDTOList);
        return "gastronomy/cocktail-all";
    }

    @GetMapping("/cocktail/{cocktailSlug}")
    public String getCocktail(@PathVariable String cocktailSlug, Model model) {
        Cocktail bySlug = cocktailService.getBySlug(cocktailSlug);
        CocktailViewDTO cocktailViewDTO = cocktailService.getCocktailViewDTO(bySlug);
        model.addAttribute(COCKTAIL, cocktailViewDTO);
        return "gastronomy/cocktail";
    }

    @GetMapping("/article/{articleId}")
    public String getArticle(@PathVariable String articleId, Model model) {
        Article byId = articleService.getById(articleId);
        ArticleDTO articleDTO = articleService.getArticleDTO(byId);
        model.addAttribute(ARTICLE, articleDTO);
        return "gastronomy/article";
    }

    @GetMapping("/country/{slug}")
    public String getRecipesByCountry(@PathVariable String slug, Model model) {
        List<Recipe> allByCountry = recipeService.getAllByCountry(Country.valueOf(slug.toUpperCase()));
        List<RecipeViewDTO> recipeViewDTOList = recipeService.getRecipeViewDTOList(allByCountry);
        model.addAttribute(COUNTRY, Country.valueOf(slug.toUpperCase()));
        model.addAttribute(RECIPE_LIST, recipeViewDTOList);
        return "gastronomy/recipe-all-by-country";
    }
}
