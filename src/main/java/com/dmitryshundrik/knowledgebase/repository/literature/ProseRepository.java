package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProseRepository extends JpaRepository<Prose, UUID> {

    Prose getBySlug(String proseSlug);

    List<Prose> getAllByOrderByCreatedDesc();

    List<Prose> getAllByWriter(Writer writer);

    List<Prose> findFirst20ByOrderByCreatedDesc();
}
