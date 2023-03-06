package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompositionRepository extends JpaRepository<Composition, UUID> {

    Composition getCompositionBySlug(String slug);

    void deleteBySlug(String slug);

    List<Composition> getAllByMusicPeriodsIsContaining(MusicPeriod musicPeriod);

    List<Composition> getAllByMusicGenresIsContaining(MusicGenre musicGenre);

    List<Composition> getAllByYearAndYearEndRankNotNull(Integer year);

}
