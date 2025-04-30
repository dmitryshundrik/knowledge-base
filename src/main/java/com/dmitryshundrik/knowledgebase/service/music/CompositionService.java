package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.model.entity.tools.SotyPair;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.util.SlugFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompositionService {

    private final CompositionRepository compositionRepository;

    public Composition getBySlug(String compositionSlug) {
        return compositionRepository.findCompositionBySlug(compositionSlug);
    }

    public List<Composition> getAll() {
        return compositionRepository.findAll();
    }

    public List<Composition> getAllByYear(Integer year) {
        return compositionRepository.findAllByYear(year);
    }

    public List<Composition> getAllByMusicianAndEssentialRankNotNull(String musicianSlug) {
        return compositionRepository.findAllByMusicianAndEssentialRankNotNull(musicianSlug);
    }

    public List<Composition> getAllByMusicianList(List<Musician> musicians) {
        List<Composition> compositions = new ArrayList<>();
        for (Musician musician : musicians) {
            compositions.addAll(musician.getCompositions());
        }
        return compositions;
    }

    public List<Composition> getAllByGenre(MusicGenre genre) {
        return compositionRepository.findAllByMusicGenresIsContaining(genre);
    }

    public List<Composition> getTop100ByClassicalGenreOrderedByRatingDesc() {
        return compositionRepository.findAllByMusicGenresIsContainingAndRatingNotNull(MusicGenreType.CLASSICAL.name(), 100);
    }

    public List<Composition> getAllOrderedByCreatedDesc() {
        return compositionRepository.findAllByOrderByCreatedDesc();
    }

    public List<Composition> getAllByMusicianWithRating(String musicianSlug) {
        return compositionRepository.findAllByMusicianWithRating(musicianSlug);
    }

    public List<Composition> getLatestUpdate() {
        return compositionRepository.findFirst20ByOrderByCreatedDesc();
    }

    public List<Integer> getAllYearsFromCompositions() {
        return compositionRepository.getAllYearsFromCompositions();
    }

    public List<CompositionViewDto> getAllForSOTYList(Integer year) {
        return getCompositionViewDtoList(compositionRepository.findAllByYearAndYearEndRankNotNull(year))
                .stream().sorted(Comparator.comparing(CompositionViewDto::getYearEndRank))
                .collect(Collectors.toList());
    }

    public List<SotyPair> getPairListForSotyGenerator(List<Composition> compositionList) {
        List<SotyPair> sotyPairList = new ArrayList<>();
        for (int i = 0; i < compositionList.size(); i++) {
            for (int j = i; j < compositionList.size() - 1; j++) {
                SotyPair pair = new SotyPair();
                pair.setFirstComposition(compositionList.get(i));
                pair.setSecondComposition(compositionList.get(j + 1));
                sotyPairList.add(pair);
            }
        }
        Collections.shuffle(sotyPairList);
        return sotyPairList;
    }

    public List<Map.Entry<Composition, Double>> getTopForSotyGenerator(List<SotyPair> staticPairList, List<SotyPair> pairList) {
        Map<Composition, Double> map = new HashMap<>();
        for (int i = 0; i < pairList.size(); i++) {
            if (pairList.get(i).isFirstResult()) {
                map.merge(staticPairList.get(i).getFirstComposition(), 1.0, Double::sum);
            }
            if (pairList.get(i).isSecondResult()) {
                map.merge(staticPairList.get(i).getSecondComposition(), 1.0, Double::sum);
            }
        }

        return map.entrySet()
                .stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).collect(Collectors.toList());
    }

    public CompositionViewDto createComposition(CompositionCreateEditDto compositionDTO, Musician musician, Album album) {
        Composition composition = new Composition();
        composition.setMusician(musician);
        composition.setAlbum(album);
        setFieldsFromDto(composition, compositionDTO);
        if (album != null) {
            album.getCompositions().add(composition);
        }
        compositionRepository.save(composition);
        composition.setSlug(SlugFormatter.slugFormatter("composition-" + composition.getSlug())
                + (composition.getYear() != null ? "-" + composition.getYear() : ""));
        return getCompositionViewDto(composition);
    }

    public CompositionViewDto updateComposition(CompositionCreateEditDto compositionDTO, String compositionSlug, Album album) {
        Composition compositionBySlug = compositionRepository.findCompositionBySlug(compositionSlug);
        compositionBySlug.setAlbum(album);
        setFieldsFromDto(compositionBySlug, compositionDTO);
        updateEssentialCompositions(compositionDTO);
        return getCompositionViewDto(compositionBySlug);
    }

    public void deleteComposition(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    public void updateEssentialCompositions(CompositionCreateEditDto compositionDto) {
        var sortedEssentialCompositionsList = getAllByMusicianAndEssentialRankNotNull(compositionDto.getMusicianSlug());
        for (int i = 0; i < sortedEssentialCompositionsList.size(); i++) {
            if (sortedEssentialCompositionsList.get(i).getEssentialCompositionsRank()
                    .equals(compositionDto.getEssentialCompositionsRank())) {
                for (int j = i; j < sortedEssentialCompositionsList.size(); j++) {
                    sortedEssentialCompositionsList.get(j)
                            .setEssentialCompositionsRank(sortedEssentialCompositionsList.get(j)
                                    .getEssentialCompositionsRank() + 1);
                }
                break;
            }
        }
    }

    public CompositionViewDto getCompositionViewDto(Composition composition) {
        return CompositionViewDto.builder()
                .created(InstantFormatter.instantFormatterDMY(composition.getCreated()))
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogTitle(composition.getMusician().getCatalogTitle())
                .catalogNumber(composition.getCatalogNumber() != null ?
                        new DecimalFormat("0.#").format(composition.getCatalogNumber()) : null)
                .musicianNickname(composition.getMusician().getNickName())
                .musicianSlug(composition.getMusician().getSlug())
                .albumTitle(composition.getAlbum() == null ? null : composition.getAlbum().getTitle())
                .feature(composition.getFeature())
                .year(composition.getYear())
                .musicGenres(composition.getMusicGenres())
                .rating(composition.getRating())
                .yearEndRank(composition.getYearEndRank())
                .essentialCompositionsRank(composition.getEssentialCompositionsRank())
                .highlights(composition.getHighlights())
                .description(composition.getDescription())
                .lyrics(composition.getLyrics())
                .translation(composition.getTranslation())
                .build();
    }

    public List<CompositionViewDto> getCompositionViewDtoList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDto)
                .collect(Collectors.toList());
    }

    public List<CompositionViewDto> getEssentialCompositionsViewDtoList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDto)
                .filter(compositionViewDTO -> compositionViewDTO.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(CompositionViewDto::getEssentialCompositionsRank))
                .collect(Collectors.toList());
    }

    public List<CompositionViewDto> getSortedCompositionViewDtoList(List<Composition> compositionList, SortType sortType) {
        return getCompositionViewDtoList(compositionList).stream()
                .sorted((o1, o2) -> {
                            if (SortType.CATALOGUE_NUMBER.equals(sortType)
                                    && o1.getCatalogNumber() != null && o2.getCatalogNumber() != null) {
                                return Double.valueOf(o1.getCatalogNumber())
                                        .compareTo(Double.valueOf(o2.getCatalogNumber()));
                            } else if (SortType.YEAR.equals(sortType)
                                    && o1.getYear() != null && o2.getYear() != null) {
                                return o1.getYear().compareTo(o2.getYear());
                            } else if (SortType.RATING.equals(sortType)
                                    && o2.getRating() != null && o1.getRating() != null) {
                                return o2.getRating().compareTo(o1.getRating());
                            }
                            return -1;
                        }
                )
                .collect(Collectors.toList());
    }

    public List<Composition> getMusicianCompositionsOrderByFacade(UUID musicianId, SortType sortType) {
        List<Composition> compositions;

        if (SortType.CATALOGUE_NUMBER.equals(sortType)) {
            compositions = getMusicianCompositionsOrderByCatalogNumber(musicianId);
        } else if (SortType.YEAR.equals(sortType)) {
            compositions = getMusicianCompositionsOrderByYear(musicianId);
        } else if (SortType.RATING.equals(sortType)) {
            compositions = getMusicianCompositionsOrderByRating(musicianId);
        } else if (SortType.CREATED.equals(sortType)) {
            compositions = getMusicianCompositionsOrderByCreated(musicianId);
        } else {
            compositions = getMusicianCompositions(musicianId);
        }
        return compositions;
    }

    private List<Composition> getMusicianCompositions(UUID musicianId) {
        return compositionRepository.findAllByMusicianId(musicianId);
    }

    private List<Composition> getMusicianCompositionsOrderByCatalogNumber(UUID musicianId) {
        return compositionRepository.findAllByMusicianIdOrderByCatalogNumber(musicianId);
    }

    private List<Composition> getMusicianCompositionsOrderByYear(UUID musicianId) {
        return compositionRepository.findAllByMusicianIdOrderByYear(musicianId);
    }

    private List<Composition> getMusicianCompositionsOrderByRating(UUID musicianId) {
        return compositionRepository.findAllByMusicianIdOrderByRating(musicianId);
    }

    private List<Composition> getMusicianCompositionsOrderByCreated(UUID musicianId) {
        return compositionRepository.findAllByMusicianIdOrderByCreated(musicianId);
    }

    public CompositionCreateEditDto getCompositionCreateEditDto(Composition composition) {
        return CompositionCreateEditDto.builder()
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogNumber(composition.getCatalogNumber())
                .musicianNickname(composition.getMusician().getNickName())
                .musicianSlug(composition.getMusician().getSlug())
                .albumId(composition.getAlbum() == null ? null : composition.getAlbum().getId().toString())
                .feature(composition.getFeature())
                .year(composition.getYear())
                .classicalGenres(composition.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CLASSICAL))
                        .collect(Collectors.toList()))
                .contemporaryGenres(composition.getMusicGenres().stream()
                        .filter(musicGenre -> musicGenre.getMusicGenreType().equals(MusicGenreType.CONTEMPORARY))
                        .collect(Collectors.toList()))
                .rating(composition.getRating())
                .yearEndRank(composition.getYearEndRank())
                .essentialCompositionsRank(composition.getEssentialCompositionsRank())
                .highlights(composition.getHighlights())
                .description(composition.getDescription())
                .lyrics(composition.getLyrics())
                .translation(composition.getTranslation())
                .lyrics(composition.getLyrics())
                .translation(composition.getTranslation())
                .build();
    }

    private void setFieldsFromDto(Composition composition, CompositionCreateEditDto compositionDto) {
        composition.setSlug(compositionDto.getSlug().trim());
        composition.setTitle(compositionDto.getTitle().trim());
        composition.setCatalogNumber(compositionDto.getCatalogNumber());
        composition.setFeature(compositionDto.getFeature().trim());
        composition.setYear(compositionDto.getYear());

        List<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(compositionDto.getClassicalGenres());
        musicGenres.addAll(compositionDto.getContemporaryGenres());

        composition.setMusicGenres(musicGenres);
        composition.setRating(compositionDto.getRating());
        composition.setYearEndRank(compositionDto.getYearEndRank());
        composition.setEssentialCompositionsRank(compositionDto.getEssentialCompositionsRank());
        composition.setHighlights(compositionDto.getHighlights());
        composition.setDescription(compositionDto.getDescription());
        composition.setLyrics(compositionDto.getLyrics());
        composition.setTranslation(compositionDto.getTranslation());
        composition.setLyrics(compositionDto.getLyrics());
        composition.setTranslation(compositionDto.getTranslation());
    }

    public Long getRepositorySize() {
        return compositionRepository.getSize();
    }
}
