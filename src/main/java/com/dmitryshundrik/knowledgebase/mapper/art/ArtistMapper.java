package com.dmitryshundrik.knowledgebase.mapper.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.Instant;

@Mapper(componentModel = "spring")
public abstract class ArtistMapper {

    @Autowired
    protected PaintingService paintingService;

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", ignore = true)
    public abstract Artist toArtist(ArtistCreateEditDto artistCreateEditDto);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", ignore = true)
    public abstract void updateArtist(@MappingTarget Artist artist, ArtistCreateEditDto artistDto);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "created", ignore = true)
    public abstract ArtistViewDto toArtistViewDto(@MappingTarget ArtistViewDto dto, Artist artist);

    @AfterMapping
    public void toArtistViewDtoPostProcess(@MappingTarget ArtistViewDto dto, Artist artist) {
        Instant created = artist.getCreated();
        dto.setCreated(created != null ? InstantFormatter.instantFormatterDMY(created) : null);
    }

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", ignore = true)
    public abstract ArtistCreateEditDto toArtistCreateEditDto(@MappingTarget ArtistCreateEditDto dto, Artist artist);

    @AfterMapping
    public void toArtistCreateEditDtoPostProcess(@MappingTarget ArtistCreateEditDto dto, Artist artist) {
        dto.setPaintingList(paintingService.getAllProfileDtoByArtistSortedByYear2Asc(artist));
    }
}
