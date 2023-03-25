package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, UUID> {

    MusicGenre getBySlug(String slug);

    List<MusicGenre> getAllByMusicGenreType(MusicGenreType type);

}
