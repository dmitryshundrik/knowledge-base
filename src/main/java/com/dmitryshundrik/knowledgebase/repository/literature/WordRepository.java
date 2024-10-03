package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Word;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface WordRepository extends JpaRepository<Word, UUID> {

    List<Word> getAllByOrderByCreatedDesc();

    List<Word> getAllByWriterOrderByCreatedDesc(Writer writer);

    List<Word> getAllByWriterOrderByTitle(Writer writer);
}
