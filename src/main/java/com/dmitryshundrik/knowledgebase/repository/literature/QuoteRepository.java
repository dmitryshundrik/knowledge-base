package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.entity.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    @Query(value = "select count(m) from Quote m")
    Long getSize();

    List<Quote> findAllByOrderByCreatedDesc();

    List<Quote> findFirst20ByOrderByCreatedDesc();

    List<Quote> findAllByWriter(Writer writer);

    List<Quote> findAllByWriterOrderByCreatedDesc(Writer writer);
}
