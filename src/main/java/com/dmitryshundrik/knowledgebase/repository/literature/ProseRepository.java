package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProseRepository extends JpaRepository<Prose, UUID> {

    Optional<Prose> findById(UUID uuid);

    Prose getBySlug(String proseSlug);

    List<Prose> getAllByOrderByCreatedDesc();

    List<Prose> getAllByWriter(Writer writer);

    List<Prose> findFirst20ByOrderByCreatedDesc();
}
