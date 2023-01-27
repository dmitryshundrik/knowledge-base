package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Long> {

    Composition getCompositionById(Long id);

    List<Composition> getAllByGenresIsContaining(Genre genre);

}
