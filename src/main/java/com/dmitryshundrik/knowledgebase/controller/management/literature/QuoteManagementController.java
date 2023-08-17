package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseSelectDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class QuoteManagementController {

    @Autowired
    private QuoteService quoteService;

    @Autowired
    private WriterService writerService;

    @Autowired
    private ProseService proseService;


    @GetMapping("/management/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute("quoteList", quoteViewDTOList);
        return "management/literature/quote-archive";
    }

    @GetMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String getQuoteCreate(@PathVariable String writerSlug, Model model) {
        QuoteCreateEditDTO quoteCreateEditDTO = new QuoteCreateEditDTO();
        quoteCreateEditDTO.setWriterNickname(writerService.getBySlug(writerSlug).getNickName());
        quoteCreateEditDTO.setWriterSlug(writerSlug);
        List<Prose> allByWriter = proseService.getAllByWriter(writerService.getBySlug(writerSlug));
        List<ProseSelectDTO> proseSelectDTOList = proseService.getProseSelectDTOList(allByWriter);
        model.addAttribute("quoteDTO", quoteCreateEditDTO);
        model.addAttribute("proseSelectDTOList", proseSelectDTOList);
        return "management/literature/quote-create";
    }

    @PostMapping("/management/writer/edit/{writerSlug}/quote/create")
    public String postQuoteCreate(@PathVariable String writerSlug,
                                  @ModelAttribute("quoteDTO") QuoteCreateEditDTO quoteDTO, Model model) {
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
        model.addAttribute("quoteDTO", quoteCreateEditDTO);
        model.addAttribute("proseSelectDTOList", proseSelectDTOList);
        return "management/literature/quote-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}/quote/edit/{quoteId}")
    public String putQuoteEdit(@PathVariable String writerSlug, @PathVariable String quoteId,
                               @ModelAttribute("quoteDTO") QuoteCreateEditDTO quoteDTO, Model model) {
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
