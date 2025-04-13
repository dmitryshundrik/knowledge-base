package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {

    List<Word> findAllByOrderByCreatedDesc();

    List<Word> findAllByWriterOrderByCreatedDesc(Writer writer);

    List<Word> findAllByWriterOrderByTitle(Writer writer);
}
