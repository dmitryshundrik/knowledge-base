package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicPeriodRepository extends JpaRepository<MusicPeriod, UUID> {

    MusicPeriod getBySlug(String slug);

}
