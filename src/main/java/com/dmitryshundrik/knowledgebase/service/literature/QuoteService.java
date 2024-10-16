package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.QuoteRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuoteService {

    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Transactional(readOnly = true)
    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Quote getById(String quoteId) {
        return quoteRepository.getById(UUID.fromString(quoteId));
    }

    @Transactional(readOnly = true)
    public List<Quote> getAllSortedByCreatedDesc() {
        return quoteRepository.getAllByOrderByCreatedDesc();
    }

    @Transactional(readOnly = true)
    public List<Quote> getAllByWriterSortedByYearAndPage(Writer writer) {
        List<Quote> allQuotes = quoteRepository.findAllByWriter(writer);
        List<Prose> allSortedProse = allQuotes.stream()
                .map(Quote::getProse)
                .filter(Objects::nonNull).distinct()
                .sorted(Comparator.comparing(Prose::getYear)).collect(Collectors.toList());

        List<Quote> allSortedQuotes = new ArrayList<>();
        for (Prose prose : allSortedProse) {
            prose.getQuoteList().sort(Comparator.comparing(Quote::getPage));
            allSortedQuotes.addAll(prose.getQuoteList());
        }
        allSortedQuotes.addAll(allQuotes.stream().filter(quote -> quote.getProse() == null).collect(Collectors.toList()));
        return allSortedQuotes;
    }

    @Transactional(readOnly = true)
    public List<Quote> getAllByWriterSortedByCreatedDesc(Writer writer) {
        return quoteRepository.getAllByWriterOrderByCreatedDesc(writer);
    }

    public Quote createQuote(QuoteCreateEditDTO quoteDTO, Writer writer, Prose prose) {
        Quote quote = new Quote();
        quote.setCreated(Instant.now());
        quote.setWriter(writer);
        quote.setProse(prose);
        quote.setPublication(quoteDTO.getPublication());
        quote.setLocation(quoteDTO.getLocation());
        quote.setPage(quoteDTO.getPage());
        quote.setDescription(quoteDTO.getDescription().trim());
        quote.setDescriptionHtml(quoteDTO.getDescriptionHtml().trim());
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(QuoteCreateEditDTO quoteDTO, String quoteId, Prose prose) {
        Quote byId = getById(quoteId);
        byId.setProse(prose);
        byId.setPublication(quoteDTO.getPublication().trim());
        byId.setLocation(quoteDTO.getLocation().trim());
        byId.setPage(quoteDTO.getPage());
        byId.setDescription(quoteDTO.getDescription().trim());
        byId.setDescriptionHtml(quoteDTO.getDescriptionHtml().trim());
        return byId;
    }

    public void deleteQuoteById(String quoteId) {
        Quote byId = getById(quoteId);
        quoteRepository.delete(byId);
    }

    public QuoteViewDTO getQuoteViewDTO(Quote quote) {
        return QuoteViewDTO.builder()
                .id(quote.getId().toString())
                .created(InstantFormatter.instantFormatterDMY(quote.getCreated()))
                .writerNickname(quote.getWriter().getNickName())
                .writerSlug(quote.getWriter().getSlug())
                .proseTitle(!(quote.getProse() == null) ? quote.getProse().getTitle() : null)
                .proseSlug(!(quote.getProse() == null) ? quote.getProse().getSlug() : null)
                .publication(quote.getPublication())
                .location(quote.getLocation())
                .page(quote.getPage())
                .description(quote.getDescription())
                .descriptionHtml(quote.getDescriptionHtml())
                .build();
    }

    public List<QuoteViewDTO> getQuoteViewDTOList(List<Quote> quoteList) {
        return quoteList.stream()
                .map(this::getQuoteViewDTO)
                .collect(Collectors.toList());
    }

    public QuoteCreateEditDTO getQuoteCreateEditDTO(Quote quote) {
        return QuoteCreateEditDTO.builder()
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
