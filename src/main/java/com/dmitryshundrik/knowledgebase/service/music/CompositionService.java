package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.*;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public Composition getCompositionBySlug(String slug) {
        return compositionRepository.getCompositionBySlug(slug);
    }

    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }

    public List<Composition> getAllCompositionsByMsuician(String musicianSlug) {
        return compositionRepository.getAllByMusician(musicianSlug);
    }

    public List<Composition> getAllCompositionsByPeriod(List<Musician> musicians) {
        List<Composition> compositions = new ArrayList<>();
        for (Musician musician : musicians) {
            compositions.addAll(musician.getCompositions());
        }
        return compositions;
    }


    public List<Composition> getAllCompositionsByGenre(MusicGenre genre) {
        return compositionRepository.getAllByMusicGenresIsContaining(genre);
    }


    public List<CompositionViewDTO> getAllCompositionsForSOTYList(Integer year) {
        return getCompositionViewDTOList(compositionRepository.getAllByYearAndYearEndRankNotNull(year))
                .stream().sorted(Comparator.comparing(CompositionViewDTO::getYearEndRank))
                .collect(Collectors.toList());
    }

    public CompositionCreateEditDTO createCompositionByDTO(CompositionCreateEditDTO compositionDTO,
                                                           Musician musician, Album album) {
        Composition composition = new Composition();
        composition.setCreated(Instant.now());
        composition.setMusician(musician);
        composition.setAlbum(album);
        setFieldsFromDTO(composition, compositionDTO);
        if (album != null) {
            album.getCompositions().add(composition);
        }
        Composition createdComposition = compositionRepository.save(composition);
        createdComposition.setSlug(createdComposition.getSlug() + "-" + createdComposition.getId());
        return getCompositionCreateEditDTO(createdComposition);
    }

    public void updateCompositionBySlug(CompositionCreateEditDTO compositionDTO, String compositionSlug, Album album) {
        Composition compositionBySlug = compositionRepository.getCompositionBySlug(compositionSlug);
        compositionBySlug.setAlbum(album);
        setFieldsFromDTO(compositionBySlug, compositionDTO);
    }

    public void deleteCompositionBySlug(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    public void updateEssentialCompositions(CompositionCreateEditDTO compositionDTO) {
        List<Composition> sortedEssentialCompositionsList = getAllCompositionsByMsuician(compositionDTO.getMusicianSlug())
                .stream().filter(composition -> composition.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(Composition::getEssentialCompositionsRank))
                .collect(Collectors.toList());
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

    public List<CompositionViewDTO> getEssentialCompositionsViewDTOList(List<Composition> compositionList) {
        return compositionList.stream().map(composition -> getCompositionViewDTO(composition))
                .filter(compositionViewDTO -> compositionViewDTO.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(CompositionViewDTO::getEssentialCompositionsRank))
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
                .build();
    }

    public CompositionViewDTO getCompositionViewDTO(Composition composition) {
        return CompositionViewDTO.builder()
                .created(Formatter.instantFormatterYMDHMS(composition.getCreated()))
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogNumber(composition.getCatalogNumber())
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
                .build();
    }

    public List<CompositionViewDTO> getCompositionViewDTOList(List<Composition> compositionList) {
        return compositionList.stream().map(composition -> getCompositionViewDTO(composition))
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

    private void setFieldsFromDTO(Composition composition, CompositionCreateEditDTO compositionDTO) {

        updateEssentialCompositions(compositionDTO);

        composition.setSlug(compositionDTO.getSlug().trim());
        composition.setTitle(compositionDTO.getTitle());
        composition.setCatalogNumber(compositionDTO.getCatalogNumber());
        composition.setFeature(compositionDTO.getFeature());
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
    }

    public List<CompositionViewDTO> getLatestUpdate() {
        return getCompositionViewDTOList(compositionRepository.findFirst10ByOrderByCreated());
    }

}
