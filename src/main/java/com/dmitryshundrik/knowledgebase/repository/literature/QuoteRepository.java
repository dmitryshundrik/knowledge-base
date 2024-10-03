package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    List<Quote> getAllByOrderByCreatedDesc();

    List<Quote> findFirst20ByOrderByCreatedDesc();

    List<Quote> findAllByWriter(Writer writer);

    List<Quote> getAllByWriterOrderByCreatedDesc(Writer writer);
}
