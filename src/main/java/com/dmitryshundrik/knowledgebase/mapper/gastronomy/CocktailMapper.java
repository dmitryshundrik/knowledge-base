package com.dmitryshundrik.knowledgebase.mapper.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.gastronomy.CocktailViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.gastronomy.Cocktail;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class CocktailMapper {

    @Autowired
    protected ImageService imageService;

    public abstract Cocktail toCocktail(CocktailCreateEditDto dto);

    public abstract void updateCocktail(@MappingTarget Cocktail cocktail, CocktailCreateEditDto dto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "imageList", ignore = true)
    public abstract CocktailViewDto toCocktailViewDto(@MappingTarget CocktailViewDto cocktailDto, Cocktail cocktail);

    @AfterMapping
    public void toCocktailViewDtoPostProcess(@MappingTarget CocktailViewDto cocktailDto, Cocktail cocktail) {
        Instant created = cocktail.getCreated();
        cocktailDto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);

        cocktailDto.setImageList(cocktail.getImageList() != null ? imageService
                .getImageDtoList(imageService
                        .getOrderByCreatedDesc(cocktail.getImageList())) : null);
    }

    @Mapping(target = "imageList", ignore = true)
    public abstract CocktailCreateEditDto toCocktailCreateEditDto(@MappingTarget CocktailCreateEditDto cocktailDto, Cocktail cocktail);

    @AfterMapping
    public void toCocktailCreateEditDtoPostProcess(@MappingTarget CocktailCreateEditDto cocktailDto, Cocktail cocktail) {
        cocktailDto.setImageList(cocktail.getImageList() != null ? imageService
                .getImageDtoList(imageService
                        .getOrderByCreatedDesc(cocktail.getImageList())) : null);
    }
}
