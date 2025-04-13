package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDTO;
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

    public Quote createQuote(QuoteCreateEditDTO quoteDTO, Writer writer, Prose prose) {
        Quote quote = new Quote();
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
                .proseTitle((quote.getProse() != null) ? quote.getProse().getTitle() : null)
                .proseSlug((quote.getProse() != null) ? quote.getProse().getSlug() : null)
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
