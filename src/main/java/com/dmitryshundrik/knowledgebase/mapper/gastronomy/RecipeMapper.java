package com.dmitryshundrik.knowledgebase.mapper.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.RecipeViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Recipe;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {

    @Autowired
    protected ImageService imageService;

    public abstract Recipe toRecipe(RecipeCreateEditDto dto);

    public abstract void updateRecipe(@MappingTarget Recipe recipe, RecipeCreateEditDto recipeDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "imageList", ignore = true)
    public abstract RecipeViewDto toRecipeViewDto(@MappingTarget RecipeViewDto recipeDto, Recipe recipe);

    @AfterMapping
    public void toRecipeViewDtoPostProcess(@MappingTarget RecipeViewDto recipeDto, Recipe recipe) {
        Instant created = recipe.getCreated();
        recipeDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);

        recipeDto.setImageList(recipe.getImageList() != null ? imageService
                .getImageDtoList(imageService
                        .getSortedByCreatedDesc(recipe.getImageList())) : null);
    }

    @Mapping(target = "imageList", ignore = true)
    public abstract RecipeCreateEditDto toRecipeCreateEditDto(@MappingTarget RecipeCreateEditDto recipeDto, Recipe recipe);

    @AfterMapping
    public void toRecipeCreateEditDtoPostProcess(@MappingTarget RecipeCreateEditDto recipeDto, Recipe recipe) {
        recipeDto.setImageList(recipe.getImageList() != null ? imageService
                .getImageDtoList(imageService
                        .getSortedByCreatedDesc(recipe.getImageList())) : null);
    }
}
