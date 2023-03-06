package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, UUID> {

    MusicGenre getBySlug(String slug);

}
