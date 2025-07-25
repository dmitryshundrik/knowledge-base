package com.dmitryshundrik.knowledgebase.service.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.service.BaseService;
import java.util.List;

/**
 * Service interface for managing {@link Recipe} entities.
 * Provides methods for retrieving, creating, updating, and deleting recipes, as well as handling
 * recipe-related data, and DTO conversions.
 */
public interface RecipeService extends BaseService {

    Recipe getBySlug(String recipeSlug);

    List<Recipe> getAll();
    List<Recipe> getAllByOrderByCreatedDesc();
    List<Recipe> getAllByCountry(String country);
    List<Recipe> getLatestUpdate();

    Recipe createRecipe(RecipeCreateEditDto recipeCreateEditDTO);
    Recipe updateRecipe(String recipeSlug, RecipeCreateEditDto recipeDTO);
    void deleteRecipe(String recipeSlug);

    RecipeViewDto getRecipeViewDto(Recipe recipe);
    List<RecipeViewDto> getRecipeViewDtoList(List<Recipe> recipeList);
    RecipeCreateEditDto getRecipeCreateEditDto(Recipe recipe);
}
