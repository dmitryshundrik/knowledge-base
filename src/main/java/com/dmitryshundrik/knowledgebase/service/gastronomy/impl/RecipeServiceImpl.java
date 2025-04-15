package com.dmitryshundrik.knowledgebase.service.gastronomy.impl;

import com.dmitryshundrik.knowledgebase.mapper.gastronomy.RecipeMapper;
import com.dmitryshundrik.knowledgebase.model.enums.Country;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.RecipeRepository;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_IS_ALREADY_EXIST;

@Service
@Transactional
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    private final RecipeMapper recipeMapper;

    private final ImageService imageService;

    @Override
    public Recipe getBySlug(String recipeSlug) {
        return recipeRepository.findBySlug(recipeSlug);
    }

    @Override
    public List<Recipe> getAll() {
        return recipeRepository.findAll();
    }

    @Override
    public List<Recipe> getAllBySortedByCreatedDesc() {
        return recipeRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Recipe> getAllByCountry(Country country) {
        return recipeRepository.findAllByCountry(country);
    }

    @Override
    public List<Recipe> getLatestUpdate() {
        return recipeRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public RecipeViewDto createRecipe(RecipeCreateEditDto recipeDto) {
        Recipe recipe = recipeMapper.toRecipe(recipeDto);
        return getRecipeViewDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeViewDto updateRecipe(String recipeSlug, RecipeCreateEditDto recipeDto) {
        Recipe bySlug = getBySlug(recipeSlug);
        recipeMapper.updateRecipe(bySlug, recipeDto);
        return getRecipeViewDto(bySlug);
    }

    @Override
    public void deleteRecipe(String recipeSlug) {
        Recipe bySlug = getBySlug(recipeSlug);
        recipeRepository.delete(bySlug);
    }

    @Override
    public RecipeViewDto getRecipeViewDto(Recipe recipe) {
        return recipeMapper.toRecipeViewDto(new RecipeViewDto(), recipe);
    }

    @Override
    public List<RecipeViewDto> getRecipeViewDtoList(List<Recipe> recipeList) {
        return recipeList.stream().map(this::getRecipeViewDto).collect(Collectors.toList());
    }

    @Override
    public RecipeCreateEditDto getRecipeCreateEditDto(Recipe recipe) {
        return recipeMapper.toRecipeCreateEditDto(new RecipeCreateEditDto(), recipe);
    }

    @Override
    public String isSlugExist(String recipeSlug) {
        String message = "";
        if (getBySlug(recipeSlug) != null) {
            message = SLUG_IS_ALREADY_EXIST;
        }
        return message;
    }

    @Override
    public Long getRepositorySize() {
        return recipeRepository.getSize();
    }
}
