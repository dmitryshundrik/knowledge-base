package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.mapper.literature.QuoteMapper;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.repository.literature.QuoteRepository;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    private final QuoteMapper quoteMapper;

    @Override
    public Quote getById(String quoteId) {
        return quoteRepository.findById(UUID.fromString(quoteId)).orElse(null);
    }

    @Override
    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    @Override
    public List<Quote> getAllOrderByCreatedDesc() {
        return quoteRepository.findAllByOrderByCreatedDesc();
    }

    @Override
    public List<Quote> getAllByWriterOrderByCreatedDesc(Writer writer) {
        return quoteRepository.findAllByWriterOrderByCreatedDesc(writer);
    }

    @Override
    public List<Quote> getLatestUpdate() {
        return quoteRepository.findFirst20ByOrderByCreatedDesc();
    }

    @Override
    public Quote createQuote(QuoteCreateEditDto quoteDto, Writer writer, Prose prose) {
        Quote quote = quoteMapper.toQuote(quoteDto);
        quote.setWriter(writer);
        quote.setProse(prose);
        return quoteRepository.save(quote);
    }

    @Override
    public Quote updateQuote(QuoteCreateEditDto quoteDto, String quoteId, Prose prose) {
        Quote quote = getById(quoteId);
        quote.setProse(prose);
        quoteMapper.updateQuote(quote, quoteDto);
        return quote;
    }

    @Override
    public void deleteQuote(String quoteId) {
        Quote quote = getById(quoteId);
        quoteRepository.delete(quote);
    }

    @Override
    public QuoteViewDto getQuoteViewDto(Quote quote) {
        return quoteMapper.toQuoteViewDto(new QuoteViewDto(), quote);
    }

    @Override
    public List<QuoteViewDto> getQuoteViewDtoList(List<Quote> quoteList) {
        return quoteList.stream()
                .map(this::getQuoteViewDto)
                .collect(Collectors.toList());
    }

    @Override
    public QuoteCreateEditDto getQuoteCreateEditDto(Quote quote) {
        return quoteMapper.toQuoteCreateEditDto(new QuoteCreateEditDto(), quote);
    }

    @Override
    public Long getRepositorySize() {
        return quoteRepository.getSize();
    }
}
