package com.dmitryshundrik.knowledgebase.mapper.music;

import com.dmitryshundrik.knowledgebase.dto.music.MusicianManagementResponseDto;
import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicianMapper {

    @Mapping(target = "musicGenres", ignore = true)
    MusicianManagementResponseDto toMusicianManagementAllResponseDto(Musician musician);
}
