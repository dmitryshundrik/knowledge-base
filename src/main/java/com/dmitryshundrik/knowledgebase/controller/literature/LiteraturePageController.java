package com.dmitryshundrik.knowledgebase.controller.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterSimpleDto;
import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Quote;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Word;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.ProseViewDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.QuoteViewDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WordDto;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterViewDto;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import com.dmitryshundrik.knowledgebase.service.literature.impl.ProseServiceImpl;
import com.dmitryshundrik.knowledgebase.service.literature.impl.QuoteServiceImpl;
import com.dmitryshundrik.knowledgebase.service.literature.impl.WordServiceImpl;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.PROSE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.QUOTE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.WORD_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER_NICKNAME;

@Controller
@RequestMapping("/literature")
@RequiredArgsConstructor
public class LiteraturePageController {

    private final WriterService writerService;

    private final ProseServiceImpl proseService;

    private final QuoteServiceImpl quoteService;

    private final WordServiceImpl wordService;

    private final ResourcesService resourcesService;

    @GetMapping
    public String getLiteraturePage() {
        return "literature/literature-page";
    }

    @GetMapping("/writer/all")
    public String getAllWriters(Model model) {
        List<WriterSimpleDto> writerDtoList = writerService.getAllOrderByBorn();
        model.addAttribute(WRITER_LIST, writerDtoList);
        return "literature/writer-all";
    }

    @GetMapping("/writer/{slug}")
    public String getWriter(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        WriterViewDto writerDto = writerService.getWriterViewDto(writerBySlug);
        List<Prose> first5ProseByWriter = proseService.getFirst5ByWriterOrderByRating(writerBySlug);
        List<ProseViewDto> first5ProseByWriterViewDtoList = proseService.getProseViewDtoList(first5ProseByWriter);
        model.addAttribute(WRITER, writerDto);
        model.addAttribute(PROSE_LIST, first5ProseByWriterViewDtoList);
        return "literature/writer";
    }

    @GetMapping("/writer/{slug}/quote/all")
    public String getWriterAllQuotes(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        List<Quote> allByWriterSortedByCreatedDesc = quoteService.getAllByWriterOrderByCreatedDesc(writerBySlug);
        List<QuoteViewDto> quoteDtoList = quoteService.getQuoteViewDtoList(allByWriterSortedByCreatedDesc);
        model.addAttribute(QUOTE_LIST, quoteDtoList);
        model.addAttribute(WRITER_NICKNAME, writerBySlug.getNickName());
        return "literature/writer-quote-all";
    }

    @GetMapping("/writer/{slug}/word/all")
    public String getWriterAllWords(@PathVariable String slug, Model model) {
        Writer writerBySlug = writerService.getBySlug(slug);
        List<Word> allByWriterSortedByTitle = wordService.getAllByWriterOrderByTitle(writerBySlug);
        List<WordDto> wordDtoList = wordService.getWordDtoList(allByWriterSortedByTitle);
        model.addAttribute(WORD_LIST, wordDtoList);
        model.addAttribute(WRITER_NICKNAME, writerBySlug.getNickName());
        return "literature/writer-word-all";
    }

    @GetMapping("/prose/all")
    public String getAllProse(Model model) {
        List<Prose> proseList = proseService.getAllOrderByCreated();
        List<ProseViewDto> proseDtoList = proseService.getProseViewDtoList(proseList);
        model.addAttribute(PROSE_LIST, proseDtoList);
        return "literature/prose-all";
    }

    @GetMapping("/quote/all")
    public String getAllQuotes(Model model) {
        List<Quote> quoteList = quoteService.getAllOrderByCreatedDesc();
        List<QuoteViewDto> quoteDtoList = quoteService.getQuoteViewDtoList(quoteList);
        model.addAttribute(QUOTE_LIST, quoteDtoList);
        return "literature/quote-all";
    }

    @GetMapping("/literature-resources")
    public String getLiteratureResources(Model model) {
        List<Resource> allByResourceType = resourcesService.getAllByResourceType(ResourceType.LITERATURE);
        model.addAttribute(RESOURCE_LIST, allByResourceType);
        return "literature/literature-resources";
    }
}
