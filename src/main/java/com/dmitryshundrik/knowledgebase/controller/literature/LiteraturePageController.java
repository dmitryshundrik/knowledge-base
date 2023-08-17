package com.dmitryshundrik.knowledgebase.controller.literature;

import com.dmitryshundrik.knowledgebase.model.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.literature.dto.ProseViewDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.QuoteViewDTO;
import com.dmitryshundrik.knowledgebase.model.literature.dto.WriterViewDTO;
import com.dmitryshundrik.knowledgebase.service.literature.ProseService;
import com.dmitryshundrik.knowledgebase.service.literature.QuoteService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/literature")
public class LiteraturePageController {

    @Autowired
    private WriterService writerService;

    @Autowired
    private ProseService proseService;

    @Autowired
    private QuoteService quoteService;


    @GetMapping()
    public String getLiteraturePage(Model model) {
        return "literature/literature-page";
    }


    @GetMapping("/writer/all")
    public String getAllWriters(Model model) {
        List<Writer> writerList = writerService.getAll();
        List<WriterViewDTO> writerViewDTOList = writerService.getWriterViewDTOList(writerList);
        model.addAttribute("writerList", writerViewDTOList);
        return "literature/writer-all";
    }

    @GetMapping("/writer/{slug}")
    public String getWriter(@PathVariable String slug, Model model) {
        Writer bySlug = writerService.getBySlug(slug);
        WriterViewDTO writerViewDTO = writerService.getWriterViewDTO(bySlug);
        List<Prose> allByWriterSortedByRating = proseService.getAllByWriterSortedByRating(bySlug);
        List<ProseViewDTO> sortedProseViewDTOList = proseService.getProseViewDTOList(allByWriterSortedByRating);
        model.addAttribute("writerDTO", writerViewDTO);
        model.addAttribute("bestProseList", sortedProseViewDTOList);
        return "literature/writer";
    }

    @GetMapping("/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAll();
        List<ProseViewDTO> proseViewDTOList = proseService.getProseViewDTOList(proseList);
        model.addAttribute("proseList", proseViewDTOList);
        return "literature/prose-all";
    }

    @GetMapping("/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllSortedByCreatedDesc();
        List<QuoteViewDTO> quoteViewDTOList = quoteService.getQuoteViewDTOList(quoteList);
        model.addAttribute("quoteList", quoteViewDTOList);
        return "literature/quote-all";
    }

}
