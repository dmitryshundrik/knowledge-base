package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface ProseRepository extends JpaRepository<Prose, UUID> {

    @Query(value = "select count(m) from Prose m")
    Long getSize();

    Prose findBySlug(String proseSlug);

    List<Prose> findAllByOrderByCreatedDesc();

    List<Prose> findAllByWriter(Writer writer);

    List<Prose> findFirst20ByOrderByCreatedDesc();

    List<Prose> findFirst5ByWriterOrderByRatingDesc(Writer writer);

    List<Prose> findAllByWriterOrderByYearAsc(Writer writer);
}
