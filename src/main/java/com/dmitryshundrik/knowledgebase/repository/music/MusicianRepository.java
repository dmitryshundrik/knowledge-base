package com.dmitryshundrik.knowledgebase.repository.music;

import com.dmitryshundrik.knowledgebase.model.music.Musician;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicianRepository extends JpaRepository<Musician, Long> {

    Musician getMusicianBySlug(String slug);

    List<Musician> getAllByOrderById();
    void deleteBySlug(String slug);

}
