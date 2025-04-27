package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.literature.impl.ProseServiceImpl;
import com.dmitryshundrik.knowledgebase.service.literature.impl.QuoteServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.PROSE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.QUOTE;
import static com.dmitryshundrik.knowledgebase.util.Constants.QUOTE_LIST;

@Controller
@RequiredArgsConstructor
public class QuoteManagementController {

    private final QuoteServiceImpl quoteService;

    private final WriterService writerService;

    private final ProseServiceImpl proseService;

    @GetMapping("/management/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllOrderByCreatedDesc();
        List<QuoteViewDto> quoteDtoList = quoteService.getQuoteViewDtoList(quoteList);
        model.addAttribute(QUOTE_LIST, quoteDtoList);
        return "management/literature/quote-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String getQuoteCreate(@PathVariable String writerSlug, Model model) {
        QuoteCreateEditDto quoteDto = new QuoteCreateEditDto();
        quoteDto.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        quoteDto.setWriterSlug(writerSlug);
        List<Prose> allByWriter = proseService.getAllByWriter(writerService.getBySlug(writerSlug));
        List<ProseSelectDto> proseDtoList = proseService.getProseSelectDtoList(allByWriter);
        model.addAttribute(QUOTE, quoteDto);
        model.addAttribute(PROSE_LIST, proseDtoList);
        return "management/literature/quote-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String postQuoteCreate(@PathVariable String writerSlug,
                                  @ModelAttribute(QUOTE) QuoteCreateEditDto quoteDto, Model model) {
        Writer writerBySlug = writerService.getBySlug(writerSlug);
        Prose proseById = !quoteDto.getProseId().isBlank() ? proseService.getById(quoteDto.getProseId()) : null;
        Quote createdQuote = quoteService.createQuote(quoteDto, writerBySlug, proseById);
        return "redirect:/management/writer/edit/" + writerSlug + "/quote/edit/" + createdQuote.getId();
    }

    @GetMapping("/management/writer/edit/{writerSlug}/quote/edit/{quoteId}")
    public String getQuoteEdit(@PathVariable String writerSlug,
                               @PathVariable String quoteId, Model model) {
        Quote quote = quoteService.getById(quoteId);
        QuoteCreateEditDto quoteDto = quoteService.getQuoteCreateEditDto(quote);
        List<Prose> proseList = proseService.getAllByWriter(writerService.getBySlug(writerSlug));
        List<ProseSelectDto> proseDtoList = proseService.getProseSelectDtoList(proseList);
        model.addAttribute(QUOTE, quoteDto);
        model.addAttribute(PROSE_LIST, proseDtoList);
        return "management/literature/quote-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/quote/edit/{quoteId}")
    public String putQuoteEdit(@PathVariable String writerSlug,
                               @PathVariable String quoteId,
                               @ModelAttribute(QUOTE) QuoteCreateEditDto quoteDto, Model model) {
        Prose proseById = quoteDto.getProseId().isBlank() ? null : proseService.getById(quoteDto.getProseId());
        Quote updatedQuote = quoteService.updateQuote(quoteDto, quoteId, proseById);
        return "redirect:/management/writer/edit/" + writerSlug + "/quote/edit/" + updatedQuote.getId();
    }


    @DeleteMapping("/management/writer/edit/{writerSlug}/quote/delete/{quoteId}")
    public String deleteWritersQuoteById(@PathVariable String writerSlug,
                                         @PathVariable String quoteId) {
        quoteService.deleteQuote(quoteId);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/quote/delete/{quoteId}")
    public String deleteQuoteById(@PathVariable String quoteId) {
        quoteService.deleteQuote(quoteId);
        return "redirect:/management/quote/all";
    }
}
