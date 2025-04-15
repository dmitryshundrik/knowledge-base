package com.dmitryshundrik.knowledgebase.controller.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Article;
import com.dmitryshundrik.knowledgebase.model.dto.core.ArticleDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.Country;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.service.core.ArticleService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.CocktailService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
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
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;

@Controller
@RequestMapping("/gastronomy")
@RequiredArgsConstructor
public class GastronomyPageController {

    private final RecipeService recipeService;

    private final CocktailService cocktailService;

    private final ArticleService articleService;

    private final ResourcesService resourcesService;

    @GetMapping
    public String getGastronomyPage(Model model) {
        List<RecipeViewDto> recipeDtoList = recipeService.getRecipeViewDtoList(recipeService.getAll());
        List<CocktailViewDto> cocktailDtoList = cocktailService.getCocktailViewDtoList(cocktailService.getAll());
        model.addAttribute(RECIPE_LIST, recipeDtoList);
        model.addAttribute(COCKTAIL_LIST, cocktailDtoList);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "gastronomy/gastronomy-page";
    }

    @GetMapping("/gastronomy-resources")
    public String getMusicResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.GASTRONOMY);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "gastronomy/gastronomy-resources";
    }

    @GetMapping("/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAll();
        List<RecipeViewDto> recipeDtoList = recipeService.getRecipeViewDtoList(recipeList);
        model.addAttribute(RECIPE_LIST, recipeDtoList);
        return "gastronomy/recipe-all";
    }

    @GetMapping("/recipe/{recipeSlug}")
    public String getRecipe(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeViewDto recipeDto = recipeService.getRecipeViewDto(bySlug);
        model.addAttribute(RECIPE, recipeDto);
        return "gastronomy/recipe";
    }

    @GetMapping("/cocktail/all")
    public String getAllCocktails(Model model) {
        List<Cocktail> cocktailList = cocktailService.getAll();
        List<CocktailViewDto> cocktailDtoList = cocktailService.getCocktailViewDtoList(cocktailList);
        model.addAttribute(COCKTAIL_LIST, cocktailDtoList);
        return "gastronomy/cocktail-all";
    }

    @GetMapping("/cocktail/{cocktailSlug}")
    public String getCocktail(@PathVariable String cocktailSlug, Model model) {
        Cocktail bySlug = cocktailService.getBySlug(cocktailSlug);
        CocktailViewDto cocktailDto = cocktailService.getCocktailViewDto(bySlug);
        model.addAttribute(COCKTAIL, cocktailDto);
        return "gastronomy/cocktail";
    }

    @GetMapping("/article/{articleId}")
    public String getArticle(@PathVariable String articleId, Model model) {
        Article byId = articleService.getById(articleId);
        ArticleDto articleDto = articleService.getArticleDto(byId);
        model.addAttribute(ARTICLE, articleDto);
        return "gastronomy/article";
    }

    @GetMapping("/country/{slug}")
    public String getRecipesByCountry(@PathVariable String slug, Model model) {
        List<Recipe> allByCountry = recipeService.getAllByCountry(Country.valueOf(slug.toUpperCase()));
        List<RecipeViewDto> recipeDtoList = recipeService.getRecipeViewDtoList(allByCountry);
        model.addAttribute(COUNTRY, Country.valueOf(slug.toUpperCase()));
        model.addAttribute(RECIPE_LIST, recipeDtoList);
        return "gastronomy/recipe-all-by-country";
    }
}
