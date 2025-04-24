package com.dmitryshundrik.knowledgebase.repository.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.UUID;

public interface QuoteRepository extends JpaRepository<Quote, UUID> {

    List<Quote> findAllByOrderByCreatedDesc();

    List<Quote> findFirst20ByOrderByCreatedDesc();

    List<Quote> findAllByWriterOrderByCreatedDesc(Writer writer);

    @Query(value = "select count(m) from Quote m")
    Long getSize();
}
