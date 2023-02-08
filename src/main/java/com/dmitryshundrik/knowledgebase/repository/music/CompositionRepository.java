package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    Composition getCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> getAllByPeriod(Period period);

    List<Composition> getAllByAcademicGenresIsContaining(AcademicGenre academicGenre);

    List<Composition> getAllByContemporaryGenresIsContaining(ContemporaryGenre contemporaryGenre);

    List<Composition> getAllByYearAndYearEndRankNotNull(Integer year);

}
