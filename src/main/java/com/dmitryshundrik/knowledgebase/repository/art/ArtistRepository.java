package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    Artist getBySlug(String artistSlug);

    List<Artist> getAllByOrderByCreatedDesc();

    List<Artist> findFirst20ByOrderByCreatedDesc();

    List<Artist> getAllByOrderByBorn();

}
