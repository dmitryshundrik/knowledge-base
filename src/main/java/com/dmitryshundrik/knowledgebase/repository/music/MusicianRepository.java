package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianManagementResponseDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianActivityDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MusicianRepository extends JpaRepository<Musician, UUID> {

    @Query(value = "select count(m) from Musician m")
    Long getSize();

    Musician findMusicianBySlug(String slug);

    Musician findMusicianByNickNameIgnoreCase(String nickName);

    Musician findMusicianByNickNameEnIgnoreCase(String nickNameEn);

    void deleteBySlug(String slug);

    List<Musician> findAllByMusicPeriodsIsContainingAndCompositionsNotEmpty(MusicPeriod period);

    List<Musician> findAllByOrderByCreated();

    List<Musician> findAllByOrderByCreatedDesc();

    @Query(value = "select * from musician where date_notification = true and extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentBirth(LocalDate date);

    @Query(value = "select * from musician where extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentDeath(LocalDate date);

    List<MusicianActivityDto> findFirst20ByOrderByCreatedDesc();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.MusicianAllPageResponseDto(musician.slug, musician.nickName, " +
            "musician.born, musician.founded) FROM Musician musician ORDER BY musician.born")
    List<MusicianAllPageResponseDto> getAllMusicianAllPageResponseDto();

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.dto.music.MusicianManagementResponseDto(musician.slug, musician.nickName, " +
            "musician.born, musician.died, musician.founded, musician.birthplace, musician.based, musician.occupation, " +
            "musician.dateNotification) FROM Musician musician ORDER BY musician.created DESC")
    List<MusicianManagementResponseDto> getAllMusicianManagementResponseDto();

    @Query(value = "select * from musician where date_notification = :isNotify and extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentBirthAndNotification(LocalDate date, Boolean isNotify);

    @Query(value = "select * from musician where date_notification = :isNotify and extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) " +
            "and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentDeathAndNotification(LocalDate date, Boolean isNotify);
}
