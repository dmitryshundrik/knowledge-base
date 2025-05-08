package com.dmitryshundrik.knowledgebase.service.music.impl;

import com.dmitryshundrik.knowledgebase.mapper.music.CompositionMapper;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_GENRES_CACHE;
import static com.dmitryshundrik.knowledgebase.util.SlugFormatter.formatCompositionSlug;

@Service
@Transactional
@RequiredArgsConstructor
public class CompositionServiceImpl implements CompositionService {

    private final CompositionRepository compositionRepository;

    private final CompositionMapper compositionMapper;

    @Override
    public Composition getBySlug(String compositionSlug) {
        return compositionRepository.findBySlug(compositionSlug);
    }

    @Override
    public List<Composition> getAllOrderByCreated() {
        return compositionRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Composition> getAllByMusicianOrderBy(UUID musicianId, SortType sortType) {
        Sort sort = Sort.unsorted();
        if (sortType != null) {
            sort = getSortForType(sortType);
        }
        return compositionRepository.findAllByMusicianId(musicianId, sort);
    }

    private Sort getSortForType(SortType sortType) {
        return switch (sortType) {
            case CATALOGUE_NUMBER -> Sort.by(Sort.Order.asc("catalogNumber"));
            case YEAR -> Sort.by(Sort.Order.asc("year"));
            case RATING -> Sort.by(Sort.Order.desc("rating"));
            case CREATED -> Sort.by(Sort.Order.desc("created"));
        };
    }

    @Override
    public List<Composition> getAllByMusicianList(List<Musician> musicians) {
        List<Composition> compositions = new ArrayList<>();
        for (Musician musician : musicians) {
            compositions.addAll(musician.getCompositions());
        }
        return compositions;
    }

    @Override
    public List<Composition> getAllByYear(Integer year) {
        return compositionRepository.findAllByYear(year);
    }

    @Override
    public List<Composition> getAllByGenre(MusicGenre genre) {
        return compositionRepository.findAllByMusicGenresIsContaining(genre);
    }

    @Override
    public List<CompositionSimpleDto> getTop100BestCompositionSimpleDto() {
        return compositionRepository.findTop100BestCompositionSimpleDtoOrderByRating(MusicGenreType.CLASSICAL);
    }

    @Override
    public List<Composition> getLatestUpdate() {
        return compositionRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public List<Integer> getAllYearsFromCompositions() {
        return compositionRepository.getAllYearsFromCompositions();
    }

    @Override
    public Map<Musician, List<Composition>> getAllByMusiciansIn(List<Musician> musicianList) {
        List<Composition> compositions = compositionRepository.findByMusicianIn(musicianList);
        return compositions.stream()
                .collect(Collectors.groupingBy(Composition::getMusician));
    }

    @Override
    @CacheEvict(value = MUSICIAN_GENRES_CACHE, key = "#musician.id")
    public Composition createComposition(CompositionCreateEditDto compositionDto, Musician musician, Album album) {
        Composition composition = compositionMapper.toComposition(compositionDto);
        composition.setMusician(musician);
        composition.setAlbum(album);
        if (album != null) {
            album.getCompositions().add(composition);
        }
        compositionRepository.save(composition);
        composition.setSlug(formatCompositionSlug(composition));
        return composition;
    }

    @Override
    public Composition updateComposition(CompositionCreateEditDto compositionDto, String compositionSlug, Album album) {
        Composition composition = compositionRepository.findBySlug(compositionSlug);
        compositionMapper.updateComposition(composition, compositionDto);
        composition.setAlbum(album);
        return composition;
    }

    @Override
    public void deleteComposition(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    @Override
    public CompositionViewDto getCompositionViewDto(Composition composition) {
        return compositionMapper.toCompositionViewDto(composition);
    }

    @Override
    public List<CompositionViewDto> getCompositionViewDtoList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompositionViewDto> getCompositionViewDtoListOrderBy(List<Composition> compositionList, SortType sortType) {
        if (compositionList == null || compositionList.isEmpty()) {
            return List.of();
        }
        SortType effectiveSortType = sortType != null ? sortType : SortType.CREATED;
        Comparator<CompositionViewDto> comparator = switch (effectiveSortType) {
            case CATALOGUE_NUMBER -> Comparator.comparing(CompositionViewDto::getCatalogNumber);
            case YEAR -> Comparator.comparing(CompositionViewDto::getYear,
                    Comparator.nullsLast(Integer::compareTo));
            case RATING -> Comparator.comparing(CompositionViewDto::getRating,
                    Comparator.nullsLast(Comparator.reverseOrder()));
            default -> Comparator.comparing(CompositionViewDto::getCreated);
        };

        return getCompositionViewDtoList(compositionList).stream()
                .sorted(comparator)
                .toList();
    }

    @Override
    public List<CompositionViewDto> getCompositionViewDtoListForSoty(Integer year) {
        return getCompositionViewDtoList(compositionRepository.findAllByYearAndYearEndRankNotNull(year))
                .stream().sorted(Comparator.comparing(CompositionViewDto::getYearEndRank))
                .collect(Collectors.toList());
    }

    @Override
    public List<CompositionViewDto> getMusicianRankCompositionViewDtoList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDto)
                .filter(compositionViewDTO -> compositionViewDTO.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(CompositionViewDto::getEssentialCompositionsRank))
                .collect(Collectors.toList());
    }

    @Override
    public CompositionCreateEditDto getCompositionCreateEditDto(Composition composition) {
        return compositionMapper.toCompositionCreateEditDto(composition);
    }

    @Override
    public Long getRepositorySize() {
        return compositionRepository.getSize();
    }
}
