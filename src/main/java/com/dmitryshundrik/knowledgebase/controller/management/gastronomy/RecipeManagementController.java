package com.dmitryshundrik.knowledgebase.controller.management.gastronomy;

import com.dmitryshundrik.knowledgebase.util.enums.Country;
import com.dmitryshundrik.knowledgebase.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.RecipeCreateEditDTO;
import com.dmitryshundrik.knowledgebase.dto.gastronomy.RecipeViewDTO;
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
        List<RecipeViewDTO> recipeDTOList = recipeService.getRecipeViewDTOList(recipeList);
        model.addAttribute(RECIPE_LIST, recipeDTOList);
        return "management/gastronomy/recipe-archive";
    }

    @GetMapping("/management/recipe/create")
    public String getRecipeCreate(Model model) {
        RecipeCreateEditDTO recipeCreateEditDTO = new RecipeCreateEditDTO();
        model.addAttribute(RECIPE, recipeCreateEditDTO);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "management/gastronomy/recipe-create";
    }

    @PostMapping("/management/recipe/create")
    public String postRecipeCreate(@Valid @ModelAttribute(RECIPE) RecipeCreateEditDTO recipeDTO,
                                   BindingResult bindingResult,
                                   Model model) {
        String error = recipeService.recipeSlugIsExist(recipeDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(COUNTRY_LIST, Country.values());
            return "management/gastronomy/recipe-create";
        }
        String recipeDTOSlug = recipeService.createRecipe(recipeDTO).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDTOSlug;
    }

    @GetMapping("/management/recipe/edit/{recipeSlug}")
    public String getRecipeEdit(@PathVariable String recipeSlug, Model model) {
        Recipe bySlug = recipeService.getBySlug(recipeSlug);
        RecipeCreateEditDTO recipeCreateEditDTO = recipeService.getRecipeCreateEditDTO(bySlug);
        model.addAttribute(RECIPE, recipeCreateEditDTO);
        model.addAttribute(COUNTRY_LIST, Country.values());
        return "management/gastronomy/recipe-edit";
    }

    @PutMapping("/management/recipe/edit/{recipeSlug}")
    public String putRecipeEdit(@PathVariable String recipeSlug,
                                @ModelAttribute("recipeDTO") RecipeCreateEditDTO recipeDTO) {
        String recipeDTOSlug = recipeService.updateRecipe(recipeSlug, recipeDTO).getSlug();
        return "redirect:/management/recipe/edit/" + recipeDTOSlug;
    }

    @DeleteMapping("/management/recipe/delete/{recipeSlug}")
    public String deleteRecipeBySlug(@PathVariable String recipeSlug) {
        recipeService.deleteRecipe(recipeSlug);
        return "redirect:/management/recipe/all";
    }
}
