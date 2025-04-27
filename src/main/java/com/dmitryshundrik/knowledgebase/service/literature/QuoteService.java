package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import java.util.List;

public interface QuoteService {

    Quote getById(String quoteId);

    List<Quote> getAll();
    List<Quote> getAllOrderByCreatedDesc();
    List<Quote> getAllByWriterOrderByCreatedDesc(Writer writer);
    List<Quote> getLatestUpdate();

    Quote createQuote(QuoteCreateEditDto quoteDto, Writer writer, Prose prose);
    Quote updateQuote(QuoteCreateEditDto quoteDto, String quoteId, Prose prose);
    void deleteQuote(String quoteId);

    QuoteViewDto getQuoteViewDto(Quote quote);
    List<QuoteViewDto> getQuoteViewDtoList(List<Quote> quoteList);
    QuoteCreateEditDto getQuoteCreateEditDto(Quote quote);

    Long getRepositorySize();
}
