package com.dmitryshundrik.knowledgebase.service.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.repository.literature.QuoteRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    public List<Quote> getAll() {
        return quoteRepository.findAll();
    }

    public List<Quote> getAllByOrderByCreatedDesc() {
        return quoteRepository.getAllByOrderByCreatedDesc();
    }

    public Quote getById(String quoteId) {
        return quoteRepository.getById(UUID.fromString(quoteId));
    }

    public Quote createQuote(QuoteCreateEditDTO quoteDTO, Writer writer, Prose prose) {
        Quote quote = new Quote();
        quote.setCreated(Instant.now());
        quote.setWriter(writer);
        quote.setProse(prose);
        quote.setLocation(quoteDTO.getLocation());
        quote.setDescription(quoteDTO.getDescription());
        return quoteRepository.save(quote);
    }

    public Quote updateQuote(QuoteCreateEditDTO quoteDTO, String quoteId, Prose prose) {
        Quote byId = getById(quoteId);
        byId.setProse(prose);
        byId.setLocation(quoteDTO.getLocation().trim());
        byId.setDescription(quoteDTO.getDescription().trim());
        return byId;
    }

    public void deleteQuoteById(String quoteId) {
        Quote byId = getById(quoteId);
        quoteRepository.delete(byId);
    }

    public QuoteViewDTO getQuoteViewDTO(Quote quote) {
        return QuoteViewDTO.builder()
                .id(quote.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(quote.getCreated()))
                .writerNickname(quote.getWriter().getNickName())
                .writerSlug(quote.getWriter().getSlug())
                .proseTitle(!(quote.getProse() == null) ? quote.getProse().getTitle() : null)
                .proseSlug(!(quote.getProse() == null) ? quote.getProse().getSlug() : null)
                .location(quote.getLocation())
                .description(quote.getDescription())
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
                .location(quote.getLocation())
                .description(quote.getDescription())
                .build();
    }

}
