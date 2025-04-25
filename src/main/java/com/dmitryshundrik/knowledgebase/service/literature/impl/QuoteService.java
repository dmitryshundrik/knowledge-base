package com.dmitryshundrik.knowledgebase.service.literature.impl;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.repository.literature.QuoteRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    public Long getQuoteRepositorySize() {
        return quoteRepository.getSize();
    }

    public Quote getById(String quoteId) {
        return quoteRepository.findById(UUID.fromString(quoteId)).orElse(null);
    }

    public List<Quote> getAllSortedByCreatedDesc() {
        return quoteRepository.findAllByOrderByCreatedDesc();
    }

    public List<Quote> getAllByWriterSortedByCreatedDesc(Writer writer) {
        return quoteRepository.findAllByWriterOrderByCreatedDesc(writer);
    }

    public Quote createQuote(QuoteCreateEditDto quoteDto, Writer writer, Prose prose) {
        Quote quote = new Quote();
        quote.setWriter(writer);
        quote.setProse(prose);
        quote.setPublication(quoteDto.getPublication());
        quote.setLocation(quoteDto.getLocation());
        quote.setPage(quoteDto.getPage());
        quote.setDescription(quoteDto.getDescription().trim());
        quote.setDescriptionHtml(quoteDto.getDescriptionHtml().trim());
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(QuoteCreateEditDto quoteDto, String quoteId, Prose prose) {
        Quote byId = getById(quoteId);
        byId.setProse(prose);
        byId.setPublication(quoteDto.getPublication().trim());
        byId.setLocation(quoteDto.getLocation().trim());
        byId.setPage(quoteDto.getPage());
        byId.setDescription(quoteDto.getDescription().trim());
        byId.setDescriptionHtml(quoteDto.getDescriptionHtml().trim());
        return byId;
    }

    public void deleteQuoteById(String quoteId) {
        Quote byId = getById(quoteId);
        quoteRepository.delete(byId);
    }

    public QuoteViewDto getQuoteViewDto(Quote quote) {
        return QuoteViewDto.builder()
                .id(quote.getId().toString())
                .created(InstantFormatter.instantFormatterDMY(quote.getCreated()))
                .writerNickname(quote.getWriter().getNickName())
                .writerSlug(quote.getWriter().getSlug())
                .proseTitle((quote.getProse() != null) ? quote.getProse().getTitle() : null)
                .proseSlug((quote.getProse() != null) ? quote.getProse().getSlug() : null)
                .publication(quote.getPublication())
                .location(quote.getLocation())
                .page(quote.getPage())
                .description(quote.getDescription())
                .descriptionHtml(quote.getDescriptionHtml())
                .build();
    }

    public List<QuoteViewDto> getQuoteViewDtoList(List<Quote> quoteList) {
        return quoteList.stream()
                .map(this::getQuoteViewDto)
                .collect(Collectors.toList());
    }

    public QuoteCreateEditDto getQuoteCreateEditDto(Quote quote) {
        return QuoteCreateEditDto.builder()
                .id(quote.getId().toString())
                .writerNickname(quote.getWriter().getNickName())
                .writerSlug(quote.getWriter().getSlug())
                .proseId(quote.getProse() == null ? null : quote.getProse().getId().toString())
                .publication(quote.getPublication())
                .location(quote.getLocation())
                .page(quote.getPage())
                .description(quote.getDescription())
                .descriptionHtml(quote.getDescriptionHtml())
                .build();
    }

    public List<Quote> getLatestUpdate() {
        return quoteRepository.findFirst20ByOrderByCreatedDesc();
    }
}
