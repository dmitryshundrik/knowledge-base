package com.dmitryshundrik.knowledgebase.mapper.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDetailedDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.InstantFormatter.instantFormatterDMY;

@Mapper(componentModel = "spring")
public interface MusicianMapper {

    @Mapping(target = "collaborations", ignore = true)
    Musician toMusician(MusicianCreateEditDto musicianDto);

    @Mapping(target = "image", ignore = true)
    @Mapping(target = "collaborations", ignore = true)
    void updateMusician(@MappingTarget Musician musician, MusicianCreateEditDto musicianDto);

    @Mapping(target = "musicGenres", ignore = true)
    MusicianArchiveDetailedDto toMusicianArchiveDetailedDto(Musician musician);

    List<MusicianSelectDto> toMusicianSelectDtoList(List<Musician> musicianList);

    @Mapping(target = "created", source = "musician", qualifiedByName = "mapCreated")
    @Mapping(target = "musicGenres", ignore = true)
    @Mapping(target = "events", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "collaborations", ignore = true)
    @Mapping(target = "compositions", ignore = true)
    @Mapping(target = "essentialCompositions", ignore = true)
    MusicianViewDto toMusicianViewDto(Musician musician);

    @Mapping(target = "events", ignore = true)
    @Mapping(target = "albums", ignore = true)
    @Mapping(target = "compositions", ignore = true)
    MusicianCreateEditDto toMusicianCreateEditDto(Musician musician);

    @Named("mapCreated")
    default String mapCreated(Musician musician) {
        return instantFormatterDMY(musician.getCreated());
    }
}
