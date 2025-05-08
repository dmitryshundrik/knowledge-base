package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CompositionService {

    Composition getBySlug(String compositionSlug);

    List<Composition> getAllOrderByCreated();
    List<Composition> getAllByMusicianOrderBy(UUID musicianId, SortType sortType);
    List<Composition> getAllByMusicianList(List<Musician> musicians);
    List<Composition> getAllByYear(Integer year);
    List<Composition> getAllByGenre(MusicGenre genre);
    List<CompositionSimpleDto> getTop100BestCompositionSimpleDto();
    List<Composition> getLatestUpdate();
    List<Integer> getAllYearsFromCompositions();
    Map<Musician, List<Composition>> getAllByMusiciansIn(List<Musician> musicianList);

    Composition createComposition(CompositionCreateEditDto compositionDto, Musician musician, Album album);
    Composition updateComposition(CompositionCreateEditDto compositionDto, String compositionSlug, Album album);
    void deleteComposition(String slug);

    CompositionViewDto getCompositionViewDto(Composition composition);
    List<CompositionViewDto> getCompositionViewDtoList(List<Composition> compositionList);
    List<CompositionViewDto> getCompositionViewDtoListOrderBy(List<Composition> compositionList, SortType sortType);
    List<CompositionViewDto> getCompositionViewDtoListForSoty(Integer year);
    List<CompositionViewDto> getMusicianRankCompositionViewDtoList(List<Composition> compositionList);
    CompositionCreateEditDto getCompositionCreateEditDto(Composition composition);

    Long getRepositorySize();

}
