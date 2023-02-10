package com.dmitryshundrik.knowledgebase.service.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionViewDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.repository.music.CompositionRepository;
import com.dmitryshundrik.knowledgebase.util.Formatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
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

    public void createCompositionByCompositionDTO(CompositionCreateEditDTO compositionCreateEditDTO, Musician musician) {
        Composition composition = new Composition();
        composition.setCreated(Instant.now());
        composition.setMusician(musician);
        setUpCompositionFieldsFromDTO(composition, compositionCreateEditDTO);
        compositionRepository.save(composition);
    }

    public void updateExistingComposition(CompositionCreateEditDTO compositionCreateEditDTO, String slug) {
        Composition compositionBySlug = compositionRepository.getCompositionBySlug(slug);
        setUpCompositionFieldsFromDTO(compositionBySlug, compositionCreateEditDTO);
    }

    public void deleteCompositionBySlug(String slug) {
        compositionRepository.deleteBySlug(slug);
    }

    public CompositionCreateEditDTO getCompositionCreateEditDTO(Composition composition) {
        return CompositionCreateEditDTO.builder()
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogNumber(composition.getCatalogNumber())
                .musicianNickname(composition.getMusician().getNickName())
                .musicianSlug(composition.getMusician().getSlug())
                .feature(composition.getFeature())
                .year(composition.getYear())
                .period(composition.getPeriod())
                .academicGenres(composition.getAcademicGenres())
                .contemporaryGenres(composition.getContemporaryGenres())
                .rating(composition.getRating())
                .yearEndRank(composition.getYearEndRank())
                .highlights(composition.getHighlights())
                .description(composition.getDescription())
                .lyrics(composition.getLyrics())
                .translation(composition.getTranslation())
                .build();
    }

    public List<CompositionViewDTO> getCompositionViewDTOList(List<Composition> compositionList) {
        return compositionList.stream().map(composition -> CompositionViewDTO.builder()
                .created(Formatter.instantFormatter(composition.getCreated()))
                .slug(composition.getSlug())
                .title(composition.getTitle())
                .catalogNumber(composition.getCatalogNumber())
                .musicianNickname(composition.getMusician().getNickName())
                .musicianSlug(composition.getMusician().getSlug())
                .feature(composition.getFeature())
                .year(composition.getYear())
                .period(composition.getPeriod())
                .academicGenres(composition.getAcademicGenres())
                .contemporaryGenres(composition.getContemporaryGenres())
                .description(composition.getDescription())
                .rating(composition.getRating())
                .yearEndRank(composition.getYearEndRank())
                .highlights(composition.getHighlights())
                .build()).collect(Collectors.toList());
    }

    private void setUpCompositionFieldsFromDTO(Composition composition, CompositionCreateEditDTO compositionCreateEditDTO) {
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
        composition.setHighlights(compositionCreateEditDTO.getHighlights());
        composition.setDescription(compositionCreateEditDTO.getDescription());
        composition.setLyrics(compositionCreateEditDTO.getLyrics());
        composition.setTranslation(compositionCreateEditDTO.getTranslation());
    }
}
