package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

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

    List<Musician> findFirst20ByOrderByCreatedDesc();

}
