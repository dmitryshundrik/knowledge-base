package com.dmitryshundrik.knowledgebase.service.gastronomy.impl;

import com.dmitryshundrik.knowledgebase.model.enums.Country;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.repository.gastronomy.RecipeRepository;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import com.dmitryshundrik.knowledgebase.service.gastronomy.RecipeService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
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
    public RecipeViewDto createRecipe(RecipeCreateEditDto recipeCreateEditDTO) {
        Recipe recipe = new Recipe();
        setFieldsFromDTO(recipe, recipeCreateEditDTO);
        return getRecipeViewDto(recipeRepository.save(recipe));
    }

    @Override
    public RecipeViewDto updateRecipe(String recipeSlug, RecipeCreateEditDto recipeDTO) {
        Recipe bySlug = getBySlug(recipeSlug);
        setFieldsFromDTO(bySlug, recipeDTO);
        return getRecipeViewDto(bySlug);
    }

    @Override
    public void deleteRecipe(String recipeSlug) {
        Recipe bySlug = getBySlug(recipeSlug);
        recipeRepository.delete(bySlug);
    }

    @Override
    public RecipeViewDto getRecipeViewDto(Recipe recipe) {
        return RecipeViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(recipe.getCreated()))
                .slug(recipe.getSlug())
                .title(recipe.getTitle())
                .country(recipe.getCountry())
                .about(recipe.getAbout())
                .ingredients(recipe.getIngredients())
                .method(recipe.getMethod())
                .imageList(recipe.getImageList() != null ? imageService
                        .getImageDtoList(imageService
                                .getSortedByCreatedDesc(recipe.getImageList())) : null)
                .build();
    }

    @Override
    public List<RecipeViewDto> getRecipeViewDtoList(List<Recipe> recipeList) {
        return recipeList.stream().map(this::getRecipeViewDto).collect(Collectors.toList());
    }

    @Override
    public RecipeCreateEditDto getRecipeCreateEditDto(Recipe recipe) {
        return RecipeCreateEditDto.builder()
                .slug(recipe.getSlug())
                .title(recipe.getTitle())
                .country(recipe.getCountry())
                .about(recipe.getAbout())
                .ingredients(recipe.getIngredients())
                .method(recipe.getMethod())
                .imageList(recipe.getImageList() != null ? imageService
                        .getImageDtoList(imageService
                                .getSortedByCreatedDesc(recipe.getImageList())) : null)
                .build();
    }

    public void setFieldsFromDTO(Recipe recipe, RecipeCreateEditDto recipeDTO) {
        recipe.setTitle(recipeDTO.getTitle());
        recipe.setSlug(recipeDTO.getSlug());
        recipe.setCountry(recipeDTO.getCountry());
        recipe.setAbout(recipeDTO.getAbout());
        recipe.setIngredients(recipeDTO.getIngredients());
        recipe.setMethod(recipeDTO.getMethod());
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
