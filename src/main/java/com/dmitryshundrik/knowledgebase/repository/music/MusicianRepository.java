package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianActivityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MusicianRepository extends JpaRepository<Musician, UUID> {

    Musician findBySlug(String slug);

    Musician findByNickNameIgnoreCase(String nickName);

    Musician findByNickNameEnIgnoreCase(String nickNameEn);

    void deleteBySlug(String slug);

    List<Musician> findAllByMusicPeriodsIsContainingAndCompositionsNotEmpty(MusicPeriod period);

    List<Musician> findAllByOrderByCreated();

    List<Musician> findAllByOrderByCreatedDesc();

    List<MusicianActivityDto> findFirst20ByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto(musician.slug, musician.nickName, " +
            "musician.born, musician.founded) FROM Musician musician ORDER BY musician.born")
    List<MusicianAllPageResponseDto> getAllMusicianAllPageResponseDto();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.MusicianArchiveDto(m.slug, m.nickName, " +
            "m.born, m.died, m.founded, m.birthplace, m.based, m.occupation, " +
            "m.dateNotification) FROM Musician m ORDER BY m.created DESC")
    List<MusicianArchiveDto> getAllMusicianManagementDto();

    @Query(value = "select * from musician where " +
            "extract(month from birth_date) = extract(month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract(day from birth_date) = extract(day from to_date(:date, 'YYYY-MM-DD')) " +
            "and (:isNotify IS NULL OR date_notification = :isNotify)", nativeQuery = true)
    List<Musician> findAllWithCurrentBirth(@Param("date") LocalDate date, @Param("isNotify") Boolean isNotify);

    @Query(value = "select * from musician where " +
            "extract(month from death_date) = extract(month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract(day from death_date) = extract(day from to_date(:date, 'YYYY-MM-DD')) " +
            "and (:isNotify IS NULL OR date_notification = :isNotify)", nativeQuery = true)
    List<Musician> findAllWithCurrentDeath(@Param("date") LocalDate date, @Param("isNotify") Boolean isNotify);

    @Query(value = "select count(m) from Musician m")
    Long getSize();
}
