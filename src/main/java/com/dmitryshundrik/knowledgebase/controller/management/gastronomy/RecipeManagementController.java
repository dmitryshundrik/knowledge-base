package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.model.enums.Country;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import javax.validation.Valid;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.COUNTRY_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RECIPE;
import static com.dmitryshundrik.knowledgebase.util.Constants.RECIPE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class RecipeManagementController {

    private final RecipeService recipeService;

    @GetMapping("/management/recipe/all")
    public String getAllRecipes(Model model) {
        List<Recipe> recipeList = recipeService.getAllBySortedByCreatedDesc();
        List<RecipeViewDto> recipeDtoList = recipeService.getRecipeViewDtoList(recipeList);
        model.addAttribute(RECIPE_LIST, recipeDtoList);
        return "management/gastronomy/recipe-archive";
    }

    @GetMapping("/management/recipe/create")
    public String getRecipeCreate(Model model) {
        RecipeCreateEditDto recipeDto = new RecipeCreateEditDto();
        model.addAttribute(RECIPE, recipeDto);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "management/gastronomy/recipe-create";
    }

    @PostMapping("/management/recipe/create")
    public String postRecipeCreate(@Valid @ModelAttribute(RECIPE) RecipeCreateEditDto recipeDto,
                                   BindingResult bindingResult,
                                   Model model) {
        String error = recipeService.isSlugExist(recipeDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(COUNTRY_LIST, Country.values());
            return "management/gastronomy/recipe-create";
        }
        String recipeDTOSlug = recipeService.createRecipe(recipeDto).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDTOSlug;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}")
    public String getRecipeEdit(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeCreateEditDto recipeDto = recipeService.getRecipeCreateEditDto(bySlug);
        model.addAttribute(RECIPE, recipeDto);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "management/gastronomy/recipe-edit";
    }

    @PutMapping("/management/recipe/edit/{recipeSlug}")
    public String putRecipeEdit(@PathVariable String recipeSlug,
                                @ModelAttribute("recipeDTO") RecipeCreateEditDto recipeDto) {
        String recipeDtoSlug = recipeService.updateRecipe(recipeSlug, recipeDto).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDtoSlug;
    }

    @DeleteMapping("/management/recipe/delete/{recipeSlug}")
    public String deleteRecipeBySlug(@PathVariable String recipeSlug) {
        recipeService.deleteRecipe(recipeSlug);
        return "redirect:/management/recipe/all";
    }
}
