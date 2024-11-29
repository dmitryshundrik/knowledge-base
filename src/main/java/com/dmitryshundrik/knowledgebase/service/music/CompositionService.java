package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.model.tools.SotyPair;
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
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CompositionService {

    private final CompositionRepository compositionRepository;

    @Transactional(readOnly = true)
    public List<Composition> getAll() {
        return compositionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Composition getCompositionBySlug(String compositionSlug) {
        return compositionRepository.getCompositionBySlug(compositionSlug);
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllByYear(Integer year) {
        return compositionRepository.getAllByYear(year);
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllByMusicianAndEssentialRankNotNull(String musicianSlug) {
        return compositionRepository.getAllByMusicianAndEssentialRankNotNull(musicianSlug);
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllByPeriod(List<Musician> musicians) {
        List<Composition> compositions = new ArrayList<>();
        for (Musician musician : musicians) {
            compositions.addAll(musician.getCompositions());
        }
        return compositions;
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllByGenre(MusicGenre genre) {
        return compositionRepository.getAllByMusicGenresIsContaining(genre);
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllOrderedByCreatedDesc() {
        return compositionRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<Composition> getAllByMusicianWithRating(String musicianSlug) {
        return compositionRepository.getAllByMusicianWithRating(musicianSlug);
    }

    @Transactional(readOnly = true)
    public List<Integer> getAllYearsFromCompositions() {
        return compositionRepository.getAllYearsFromCompositions();
    }

    public List<CompositionViewDTO> getAllForSOTYList(Integer year) {
        return getCompositionViewDTOList(compositionRepository.getAllByYearAndYearEndRankNotNull(year))
                .stream().sorted(Comparator.comparing(CompositionViewDTO::getYearEndRank))
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

    public CompositionViewDTO createComposition(CompositionCreateEditDTO compositionDTO, Musician musician, Album album) {
        Composition composition = new Composition();
        composition.setMusician(musician);
        composition.setAlbum(album);
        setFieldsFromDTO(composition, compositionDTO);
        if (album != null) {
            album.getCompositions().add(composition);
        }
        compositionRepository.save(composition);
        composition.setSlug(SlugFormatter.slugFormatter("composition-" + composition.getSlug()) + "-"
                + (composition.getYear() != null ? composition.getYear().toString() : ""));
        return getCompositionViewDTO(composition);
    }

    public CompositionViewDTO updateComposition(CompositionCreateEditDTO compositionDTO, String compositionSlug, Album album) {
        Composition compositionBySlug = compositionRepository.getCompositionBySlug(compositionSlug);
        compositionBySlug.setAlbum(album);
        setFieldsFromDTO(compositionBySlug, compositionDTO);
        updateEssentialCompositions(compositionDTO);
        return getCompositionViewDTO(compositionBySlug);
    }

    public void deleteCompositionBySlug(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    public void updateEssentialCompositions(CompositionCreateEditDTO compositionDTO) {
        var sortedEssentialCompositionsList = getAllByMusicianAndEssentialRankNotNull(compositionDTO.getMusicianSlug());
        for (int i = 0; i < sortedEssentialCompositionsList.size(); i++) {
            if (sortedEssentialCompositionsList.get(i).getEssentialCompositionsRank()
                    .equals(compositionDTO.getEssentialCompositionsRank())) {
                for (int j = i; j < sortedEssentialCompositionsList.size(); j++) {
                    sortedEssentialCompositionsList.get(j)
                            .setEssentialCompositionsRank(sortedEssentialCompositionsList.get(j)
                                    .getEssentialCompositionsRank() + 1);
                }
                break;
            }
        }
    }

    public CompositionViewDTO getCompositionViewDTO(Composition composition) {
        return CompositionViewDTO.builder()
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

    public List<CompositionViewDTO> getCompositionViewDTOList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDTO)
                .collect(Collectors.toList());
    }

    public List<CompositionViewDTO> getEssentialCompositionsViewDTOList(List<Composition> compositionList) {
        return compositionList.stream().map(this::getCompositionViewDTO)
                .filter(compositionViewDTO -> compositionViewDTO.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(CompositionViewDTO::getEssentialCompositionsRank))
                .collect(Collectors.toList());
    }

    public List<CompositionViewDTO> getSortedCompositionViewDTOList(List<Composition> compositionList, SortType sortType) {
        return getCompositionViewDTOList(compositionList).stream()
                .sorted((o1, o2) -> {
                            if (SortType.CATALOGUE_NUMBER.equals(sortType)
                                    && o1.getCatalogNumber() != null && o2.getCatalogNumber() != null) {
                                return o1.getCatalogNumber().compareTo(o2.getCatalogNumber());
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

    public CompositionCreateEditDTO getCompositionCreateEditDTO(Composition composition) {
        return CompositionCreateEditDTO.builder()
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

    private void setFieldsFromDTO(Composition composition, CompositionCreateEditDTO compositionDTO) {
        composition.setSlug(compositionDTO.getSlug().trim());
        composition.setTitle(compositionDTO.getTitle().trim());
        composition.setCatalogNumber(compositionDTO.getCatalogNumber());
        composition.setFeature(compositionDTO.getFeature().trim());
        composition.setYear(compositionDTO.getYear());

        List<MusicGenre> musicGenres = new ArrayList<>();
        musicGenres.addAll(compositionDTO.getClassicalGenres());
        musicGenres.addAll(compositionDTO.getContemporaryGenres());

        composition.setMusicGenres(musicGenres);
        composition.setRating(compositionDTO.getRating());
        composition.setYearEndRank(compositionDTO.getYearEndRank());
        composition.setEssentialCompositionsRank(compositionDTO.getEssentialCompositionsRank());
        composition.setHighlights(compositionDTO.getHighlights());
        composition.setDescription(compositionDTO.getDescription());
        composition.setLyrics(compositionDTO.getLyrics());
        composition.setTranslation(compositionDTO.getTranslation());
        composition.setLyrics(compositionDTO.getLyrics());
        composition.setTranslation(compositionDTO.getTranslation());
    }

    public List<Composition> getLatestUpdate() {
        return compositionRepository.findFirst20ByOrderByCreatedDesc();
    }
}
