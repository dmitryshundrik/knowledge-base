package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianAllDto;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianEntityUpdateInfoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface MusicianRepository extends JpaRepository<Musician, UUID> {

    Musician getMusicianBySlug(String slug);

    Musician getMusicianByNickNameIgnoreCase(String nickName);

    Musician getMusicianByNickNameEnIgnoreCase(String nickNameEn);

    List<Musician> getAllByMusicPeriodsIsContaining(MusicPeriod period);

    List<Musician> getAllByOrderByCreated();

    List<Musician> getAllByOrderByCreatedDesc();

    void deleteBySlug(String slug);

    List<MusicianEntityUpdateInfoDTO> findFirst20ByOrderByCreatedDesc();

    @Query(value = "select * from musician where extract( month from birth_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from birth_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentBirth(LocalDate date);

    @Query(value = "select * from musician where extract( month from death_date) = extract( month from to_date(:date, 'YYYY-MM-DD')) and extract( day from death_date) = extract( day from to_date(:date, 'YYYY-MM-DD'))", nativeQuery = true)
    List<Musician> findAllWithCurrentDeath(LocalDate date);

    @Query("SELECT new com.dmitryshundrik.knowledgebase.model.music.dto.MusicianAllDto(musician.slug, musician.nickName, musician.born, musician.founded) FROM Musician musician ORDER BY musician.born")
    List<MusicianAllDto> getAllMusicianAllDto();
}
