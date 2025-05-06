package com.dmitryshundrik.knowledgebase.mapper.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(target = "musician", ignore = true)
    @Mapping(target = "collaborators", ignore = true)
    @Mapping(target = "musicGenres", ignore = true)
    Album toAlbum(AlbumCreateEditDto albumDto);

    @Mapping(target = "musician", ignore = true)
    @Mapping(target = "collaborators", ignore = true)
    @Mapping(target = "musicGenres", ignore = true)
    void updateAlbum(@MappingTarget Album album, AlbumCreateEditDto albumDto);

    @Mapping(target = "created", ignore = true)
    @Mapping(target = "musicianNickname", source = "musician.nickName")
    @Mapping(target = "musicianSlug", source = "musician.slug")
    @Mapping(target = "collaborators", ignore = true)
    AlbumViewDto toAlbumViewDto(Album album);

    @Mapping(target = "musicianNickname", source = "musician.nickName")
    @Mapping(target = "musicianSlug", source = "musician.slug")
    @Mapping(target = "collaboratorsUUID", ignore = true)
    AlbumCreateEditDto toAlbumCreateEditDto(Album album);

}
