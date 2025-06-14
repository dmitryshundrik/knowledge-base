package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MusicPeriodRepository extends JpaRepository<MusicPeriod, UUID> {

    Optional<MusicPeriod> findBySlug(String slug);

    List<MusicPeriod> findAllByOrderByApproximateStartAsc();
}
