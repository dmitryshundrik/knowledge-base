package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WriterRepository extends JpaRepository<Writer, UUID> {

    Writer findBySlug(String writerSlug);

    void deleteBySlug(String writerSlug);

    List<Writer> getAllByOrderByCreatedDesc();

    List<Writer> findFirst20ByOrderByCreatedDesc();

}
