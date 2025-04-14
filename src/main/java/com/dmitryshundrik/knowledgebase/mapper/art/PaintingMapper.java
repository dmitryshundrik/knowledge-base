package com.dmitryshundrik.knowledgebase.mapper.art;

import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.PaintingViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.service.core.ImageService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class PaintingMapper {

    @Autowired
    protected ImageService imageService;

    @Mapping(target = "slug", expression = "java(paintingDto.getSlug() != null ? paintingDto.getSlug().trim() : null)")
    @Mapping(target = "title", expression = "java(paintingDto.getTitle() != null ? paintingDto.getTitle().trim() : null)")
    @Mapping(target = "approximateYears", expression = "java(paintingDto.getApproximateYears() != null ? paintingDto.getApproximateYears().trim() : null)")
    @Mapping(target = "based", expression = "java(paintingDto.getBased() != null ? paintingDto.getBased().trim() : null)")
    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "paintingStyles", ignore = true)
    @Mapping(target = "image", ignore = true)
    public abstract Painting toPainting(@MappingTarget Painting painting, PaintingCreateEditDto paintingDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "artistNickname", source = "artist.nickName")
    @Mapping(target = "artistSlug", source = "artist.slug")
    @Mapping(target = "paintingStyles", ignore = true)
    @Mapping(target = "image", ignore = true)
    public abstract PaintingViewDto toPaintingViewDto(@MappingTarget PaintingViewDto dto, Painting painting);

    @AfterMapping
    public void toPaintingViewDtoPostProcess(@MappingTarget PaintingViewDto dto, Painting painting) {
        Instant created = painting.getCreated();
        dto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
        dto.setImage(imageService.getImageDto(painting.getImage()));
    }

    @Mapping(target = "artistNickname", source = "artist.nickName")
    @Mapping(target = "artistSlug", source = "artist.slug")
    @Mapping(target = "paintingStyles", ignore = true)
    @Mapping(target = "image", ignore = true)
    public abstract PaintingCreateEditDto toPaintingCreateEditDto(@MappingTarget PaintingCreateEditDto dto, Painting painting);

    @AfterMapping
    public void toPaintingCreateEditDtoPostProcess(@MappingTarget PaintingCreateEditDto dto, Painting painting) {
        dto.setImage(imageService.getImageDto(painting.getImage()));
    }
}
