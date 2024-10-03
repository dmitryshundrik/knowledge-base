package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface MusicGenreRepository extends JpaRepository<MusicGenre, UUID> {

    MusicGenre getBySlug(String slug);

    List<MusicGenre> getAllByMusicGenreTypeAndCountIsNotNullOrderByCountDesc(MusicGenreType type);

    @Modifying
    @Query(nativeQuery = true, value = "update music_genre set count = (select count(comp.id) from music_genre mg\n" +
            "inner join composition_music_genres cmg on cmg.music_genres_id = mg.id\n" +
            "inner join composition comp on cmg.composition_id = comp.id\n" +
            "where mg.id = music_genre.id\n" +
            "group by mg.id) where music_genre_type = 'CLASSICAL'")
    void updateClassicalMusicGenreSetCount();

    @Modifying
    @Query(nativeQuery = true, value = "update music_genre set count = (select count(alb.id) from music_genre mg\n" +
            "inner join album_music_genres amg on amg.music_genres_id = mg.id\n" +
            "inner join album alb on amg.album_id = alb.id\n" +
            "where mg.id = music_genre.id\n" +
            "group by mg.id) where music_genre_type = 'CONTEMPORARY'")
    void updateContemporaryMusicGenreSetCount();
}
