package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.YearInMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface YearInMusicRepository extends JpaRepository<YearInMusic, UUID> {

    YearInMusic findBySlug(String slug);

}
