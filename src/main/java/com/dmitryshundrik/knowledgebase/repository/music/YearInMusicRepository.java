package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.YearInMusic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface YearInMusicRepository extends JpaRepository<YearInMusic, UUID> {

    Optional<YearInMusic> findBySlug(String slug);

    Optional<YearInMusic> findByYear(Integer year);

    List<YearInMusic> findAllByOrderByYearAsc();

    @Query(value = "SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.YearInMusicSimpleDto(y.slug, y.title) " +
            "FROM YearInMusic y " +
            "ORDER BY y.year ASC")
    List<YearInMusicSimpleDto> findAllOrderByYearAcs();

    Boolean existsByYear(Integer year);
}
