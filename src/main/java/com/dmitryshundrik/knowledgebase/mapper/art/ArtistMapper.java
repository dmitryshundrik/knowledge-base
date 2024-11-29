package com.dmitryshundrik.knowledgebase.mapper.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.service.art.PaintingService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", imports = PaintingService.class)
public interface ArtistMapper {

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", ignore = true)
    Artist toArtist(ArtistCreateEditDto artistCreateEditDto);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "paintingList", ignore = true)
    void updateArtist(ArtistCreateEditDto artistDto, @MappingTarget Artist artist);

    @Mapping(target = "events", ignore = true)
    ArtistViewDto toArtistViewDto(Artist artist);
}
