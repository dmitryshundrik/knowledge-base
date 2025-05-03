package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import java.util.List;
import java.util.UUID;

public interface CompositionService {

    Composition getBySlug(String compositionSlug);

    List<Composition> getAll();
    List<Composition> getAllOrderByCreatedDesc();
    List<Composition> getAllByMusicianOrderBy(UUID musicianId, SortType sortType);
    List<Composition> getAllByMusicianAndEssentialRankNotNull(String musicianSlug);
    List<Composition> getAllByMusicianWithRating(String musicianSlug);
    List<Composition> getAllByMusicianList(List<Musician> musicians);
    List<Composition> getAllByYear(Integer year);
    List<Composition> getAllByGenre(MusicGenre genre);
    List<Composition> getTop100ByClassicalGenreOrderByRatingDesc();
    List<Composition> getLatestUpdate();
    List<Integer> getAllYearsFromCompositions();

    List<CompositionViewDto> getAllForSOTYList(Integer year);

    CompositionViewDto createComposition(CompositionCreateEditDto compositionDto, Musician musician, Album album);
    CompositionViewDto updateComposition(CompositionCreateEditDto compositionDto, String compositionSlug, Album album);
    void deleteComposition(String slug);
    void updateEssentialCompositions(CompositionCreateEditDto compositionDto);

    CompositionViewDto getCompositionViewDto(Composition composition);
    List<CompositionViewDto> getCompositionViewDtoList(List<Composition> compositionList);
    List<CompositionViewDto> getEssentialCompositionsViewDtoList(List<Composition> compositionList);
    List<CompositionViewDto> getSortedCompositionViewDtoList(List<Composition> compositionList, SortType sortType);
    CompositionCreateEditDto getCompositionCreateEditDto(Composition composition);

    Long getRepositorySize();

}
