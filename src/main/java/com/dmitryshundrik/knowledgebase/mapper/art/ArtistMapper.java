package com.dmitryshundrik.knowledgebase.mapper.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

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
    public abstract ArtistViewDto toArtistViewDto(Artist artist);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", expression = "java(paintingService" +
            ".getPaintingViewDtoList(paintingService.getAllByArtistSortedByYear2(artist)))")
    public abstract ArtistCreateEditDto toArtistCreateEditDto(Artist artist);
}
