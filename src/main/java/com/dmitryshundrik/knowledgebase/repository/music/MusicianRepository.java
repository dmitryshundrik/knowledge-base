package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MusicianRepository extends JpaRepository<Musician, UUID> {

    Musician getMusicianBySlug(String slug);

    List<Musician> getAllByMusicPeriodsIsContaining(MusicPeriod period);

    List<Musician> getAllByMusicGenresIsContaining(MusicGenre genre);

    List<Musician> getAllByOrderByCreated();

    void deleteBySlug(String slug);

}
