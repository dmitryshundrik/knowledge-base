package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    List<Composition> getAllByPeriod(Period period);

    List<Composition> getAllByGenresIsContaining(Genre genre);

    List<Composition> getAllByYearAndYearEndRankNotNull(Integer year);

}
