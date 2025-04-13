package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseSelectDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
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

    private final QuoteService quoteService;

    private final WriterService writerService;

    private final ProseService proseService;

    @GetMapping("/management/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute(QUOTE_LIST, quoteViewDTOList);
        return "management/literature/quote-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String getQuoteCreate(@PathVariable String writerSlug, Model model) {
        QuoteCreateEditDTO quoteCreateEditDTO = new QuoteCreateEditDTO();
        quoteCreateEditDTO.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        quoteCreateEditDTO.setWriterSlug(writerSlug);
        List<Prose> allByWriter = proseService.getAllByWriter(writerService.getBySlug(writerSlug));
        List<ProseSelectDTO> proseSelectDTOList = proseService.getProseSelectDTOList(allByWriter);
        model.addAttribute(QUOTE, quoteCreateEditDTO);
        model.addAttribute(PROSE_LIST, proseSelectDTOList);
        return "management/literature/quote-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String postQuoteCreate(@PathVariable String writerSlug,
                                  @ModelAttribute(QUOTE) QuoteCreateEditDTO quoteDTO, Model model) {
        Writer writerBySlug = writerService.getBySlug(writerSlug);
        Prose proseById = !quoteDTO.getProseId().isBlank() ? proseService.getById(quoteDTO.getProseId()) : null;
        Quote createdQuote = quoteService.createQuote(quoteDTO, writerBySlug, proseById);
        return "redirect:/management/writer/edit/" + writerSlug + "/quote/edit/" + createdQuote.getId();
    }

    @GetMapping("/management/writer/edit/{writerSlug}/quote/edit/{quoteId}")
    public String getQuoteEdit(@PathVariable String writerSlug, @PathVariable String quoteId, Model model) {
        Quote quote = quoteService.getById(quoteId);
        QuoteCreateEditDTO quoteCreateEditDTO = quoteService.getQuoteCreateEditDTO(quote);
        List<Prose> allByWriter = proseService.getAllByWriter(writerService.getBySlug(writerSlug));
        List<ProseSelectDTO> proseSelectDTOList = proseService.getProseSelectDTOList(allByWriter);
        model.addAttribute(QUOTE, quoteCreateEditDTO);
        model.addAttribute(PROSE_LIST, proseSelectDTOList);
        return "management/literature/quote-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/quote/edit/{quoteId}")
    public String putQuoteEdit(@PathVariable String writerSlug, @PathVariable String quoteId,
                               @ModelAttribute(QUOTE) QuoteCreateEditDTO quoteDTO, Model model) {
        Prose proseById = !quoteDTO.getProseId().isBlank() ? proseService.getById(quoteDTO.getProseId()) : null;
        Quote updatedQuote = quoteService.updateQuote(quoteDTO, quoteId, proseById);
        return "redirect:/management/writer/edit/" + writerSlug + "/quote/edit/" + updatedQuote.getId();
    }


    @DeleteMapping("/management/writer/edit/{writerSlug}/quote/delete/{quoteId}")
    public String deleteWritersQuoteById(@PathVariable String writerSlug, @PathVariable String quoteId) {
        quoteService.deleteQuoteById(quoteId);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/quote/delete/{quoteId}")
    public String deleteQuoteById(@PathVariable String quoteId) {
        quoteService.deleteQuoteById(quoteId);
        return "redirect:/management/quote/all";
    }
}
