package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompositionService {

    @Autowired
    private CompositionRepository compositionRepository;

    public List<Composition> getAllCompositions() {
        return compositionRepository.findAll();
    }

    public Composition getCompositionBySlug(String slug) {
        return compositionRepository.getCompositionBySlug(slug);
    }

    public List<Composition> getAllCompositionsByPeriod(Period period) {
        return compositionRepository.getAllByPeriod(period);
    }

    public List<Composition> getAllCompositionsByAcademicGenre(AcademicGenre academicGenre) {
        return compositionRepository.getAllByAcademicGenresIsContaining(academicGenre);
    }

    public List<Composition> getAllCompositionsByContemporaryGenre(ContemporaryGenre contemporaryGenre) {
        return compositionRepository.getAllByContemporaryGenresIsContaining(contemporaryGenre);
    }

    public List<Composition> getAllCompositionsBySOTYList(SOTYList sotyList) {
        return compositionRepository.getAllByYearAndYearEndRankNotNull(sotyList.getYear());
    }

    public CompositionCreateEditDTO createCompositionByDTO(CompositionCreateEditDTO compositionCreateEditDTO, Musician musician, Album album) {
        Composition composition = new Composition();
        composition.setCreated(Instant.now());
        composition.setMusician(musician);
        composition.setAlbum(album);
        setCompositionFieldsFromDTO(composition, compositionCreateEditDTO);
        if (album != null) {
            album.getCompositions().add(composition);
        }
        Composition createdComposition = compositionRepository.save(composition);
        createdComposition.setSlug(createdComposition.getSlug() + "-" + createdComposition.getId());
        return getCompositionCreateEditDTO(createdComposition);
    }

    public void updateExistingComposition(CompositionCreateEditDTO compositionCreateEditDTO, String slug, Album album) {
        Composition compositionBySlug = compositionRepository.getCompositionBySlug(slug);
        compositionBySlug.setAlbum(album);
        setCompositionFieldsFromDTO(compositionBySlug, compositionCreateEditDTO);
    }

    public void deleteCompositionBySlug(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    public void updateEssentialCompositions(CompositionCreateEditDTO compositionCreateEditDTO) {
        List<Composition> sortedEssentialCompositionsList = getAllCompositions()
                .stream().filter(composition -> composition.getEssentialCompositionsRank() != null)
                .sorted(Comparator.comparing(Composition::getEssentialCompositionsRank))
                .collect(Collectors.toList());
        for (int i = 0; i < sortedEssentialCompositionsList.size(); i++) {
            if (sortedEssentialCompositionsList.get(i).getEssentialCompositionsRank()
                    .equals(compositionCreateEditDTO.getEssentialCompositionsRank())) {
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
                .period(composition.getPeriod())
                .academicGenres(composition.getAcademicGenres())
                .contemporaryGenres(composition.getContemporaryGenres())
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
                .created(Formatter.instantFormatter(composition.getCreated()))
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogNumber(composition.getCatalogNumber())
                .musicianNickname(composition.getMusician().getNickName())
                .musicianSlug(composition.getMusician().getSlug())
                .albumTitle(composition.getAlbum() == null ? null : composition.getAlbum().getTitle())
                .feature(composition.getFeature())
                .year(composition.getYear())
                .period(composition.getPeriod())
                .academicGenres(composition.getAcademicGenres())
                .contemporaryGenres(composition.getContemporaryGenres())
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
                            if (SortType.CATALOGUE_NUMBER.equals(sortType)) {
                                return o1.getCatalogNumber().compareTo(o2.getCatalogNumber());
                            } else if (SortType.YEAR.equals(sortType)) {
                                return o1.getYear().compareTo(o2.getYear());
                            } else if (SortType.RATING.equals(sortType)) {
                                return o1.getRating().compareTo(o2.getRating());
                            }
                            return -1;
                        }
                )
                .collect(Collectors.toList());
    }

    private void setCompositionFieldsFromDTO(Composition composition, CompositionCreateEditDTO compositionCreateEditDTO) {

        updateEssentialCompositions(compositionCreateEditDTO);

        composition.setSlug(compositionCreateEditDTO.getSlug());
        composition.setTitle(compositionCreateEditDTO.getTitle());
        composition.setCatalogNumber(compositionCreateEditDTO.getCatalogNumber());
        composition.setFeature(compositionCreateEditDTO.getFeature());
        composition.setYear(compositionCreateEditDTO.getYear());
        composition.setPeriod(compositionCreateEditDTO.getPeriod());
        composition.setAcademicGenres(compositionCreateEditDTO.getAcademicGenres());
        composition.setContemporaryGenres(compositionCreateEditDTO.getContemporaryGenres());
        composition.setRating(compositionCreateEditDTO.getRating());
        composition.setYearEndRank(compositionCreateEditDTO.getYearEndRank());
        composition.setEssentialCompositionsRank(compositionCreateEditDTO.getEssentialCompositionsRank());
        composition.setHighlights(compositionCreateEditDTO.getHighlights());
        composition.setDescription(compositionCreateEditDTO.getDescription());
        composition.setLyrics(compositionCreateEditDTO.getLyrics());
        composition.setTranslation(compositionCreateEditDTO.getTranslation());
    }
}
