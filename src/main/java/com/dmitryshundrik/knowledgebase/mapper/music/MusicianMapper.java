package com.dmitryshundrik.knowledgebase.mapper.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianManagementDetailedResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicianMapper {

    @Mapping(target = "musicGenres", ignore = true)
    MusicianManagementDetailedResponseDto toMusicianManagementDetailedResponseDto(Musician musician);
}
