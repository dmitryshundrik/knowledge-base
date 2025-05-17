package com.dmitryshundrik.knowledgebase.mapper.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.dmitryshundrik.knowledgebase.util.InstantFormatter.instantFormatterDMY;

@Mapper(componentModel = "spring")
public interface CompositionMapper {

    @Mapping(target = "musician", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "musicGenres", source = "compositionDto", qualifiedByName = "mapMusicGenres")
    Composition toComposition(CompositionCreateEditDto compositionDto);

    @Mapping(target = "musician", ignore = true)
    @Mapping(target = "album", ignore = true)
    @Mapping(target = "musicGenres", source = "compositionDto", qualifiedByName = "mapMusicGenres")
    void updateComposition(@MappingTarget Composition composition, CompositionCreateEditDto compositionDto);

    @Mapping(target = "created", source = "composition", qualifiedByName = "mapCreated")
    @Mapping(target = "catalogTitle", source = "composition", qualifiedByName = "mapCatalogTitle")
    @Mapping(target = "catalogNumber", source = "composition", qualifiedByName = "mapCatalogNumber")
    @Mapping(target = "musicianNickname", source = "musician.nickName")
    @Mapping(target = "musicianSlug", source = "musician.slug")
    @Mapping(target = "albumId", source = "album", qualifiedByName = "mapAlbumId")
    @Mapping(target = "albumTitle", source = "album", qualifiedByName = "mapAlbumTitle")
    CompositionViewDto toCompositionViewDto(Composition composition);

    @Mapping(target = "musicianNickname", source = "musician.nickName")
    @Mapping(target = "musicianSlug", source = "musician.slug")
    @Mapping(target = "albumId", source = "album", qualifiedByName = "mapAlbumId")
    @Mapping(target = "classicalGenres", source = "composition", qualifiedByName = "mapClassicalMusicGenres")
    @Mapping(target = "contemporaryGenres", source = "composition", qualifiedByName = "mapContemporaryMusicGenres")
    CompositionCreateEditDto toCompositionCreateEditDto(Composition composition);

    @Named("mapCreated")
    default String mapCreated(Composition composition) {
        return instantFormatterDMY(composition.getCreated());
    }

    @Named("mapMusicGenres")
    default List<MusicGenre> mapMusicGenres(CompositionCreateEditDto compositionDto) {
        return Stream.of(Optional.ofNullable(
                compositionDto.getClassicalGenres()).orElse(List.of()), Optional.ofNullable(
                compositionDto.getContemporaryGenres()).orElse(List.of())).flatMap(List::stream).toList();
    }

    @Named("mapClassicalMusicGenres")
    default List<MusicGenre> mapClassicalMusicGenres(Composition composition) {
        return composition.getMusicGenres().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                .collect(Collectors.toList());
    }

    @Named("mapContemporaryMusicGenres")
    default List<MusicGenre> mapContemporaryMusicGenres(Composition composition) {
        return composition.getMusicGenres().stream()
                .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                .collect(Collectors.toList());
    }

    @Named("mapCatalogTitle")
    default String mapCatalogTitle(Composition composition) {
        return composition.getMusician().getCatalogTitle() != null ? composition.getMusician().getCatalogTitle() : "";
    }

    @Named("mapCatalogNumber")
    default String mapCatalogNumber(Composition composition) {
        return composition.getCatalogNumber() != null ? new DecimalFormat("0.#")
                .format(composition.getCatalogNumber()) : null;
    }

    @Named("mapAlbumId")
    default String mapAlbumId(Album album) {
        return album != null ? album.getId().toString() : null;
    }

    @Named("mapAlbumTitle")
    default String mapAlbumTitle(Album album) {
        return album != null ? album.getTitle() : null;
    }
}
