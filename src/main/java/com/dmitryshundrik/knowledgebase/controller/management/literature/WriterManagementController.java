package com.dmitryshundrik.knowledgebase.controller.management.literature;

import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterArchiveListDto;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Writer;
import com.dmitryshundrik.knowledgebase.model.dto.literature.WriterCreateEditDto;
import com.dmitryshundrik.knowledgebase.service.literature.WriterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.GENDER_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER;
import static com.dmitryshundrik.knowledgebase.util.Constants.WRITER_LIST;

@Controller
@RequiredArgsConstructor
public class WriterManagementController {

    private final WriterService writerService;

    @GetMapping("/management/writer/all")
    public String getAllWriters(Model model) {
        List<WriterArchiveListDto> writerDtoList = writerService.getAllOrderByCreated();
        model.addAttribute(WRITER_LIST, writerDtoList);
        return "management/literature/writer-archive";
    }

    @GetMapping("/management/writer/create")
    public String getWriterCreate(Model model) {
        WriterCreateEditDto writerDto = new WriterCreateEditDto();
        model.addAttribute(WRITER, writerDto);
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/literature/writer-create";
    }

    @PostMapping("/management/writer/create")
    public String postWriterCreate(@Valid @ModelAttribute(WRITER) WriterCreateEditDto writerDto, BindingResult bindingResult,
                                   Model model) {
        String error = writerService.isSlugExists(writerDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(GENDER_LIST, Gender.values());
            return "management/literature/writer-create";
        }
        String writerSlug = writerService.createWriter(writerDto).getSlug();
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @GetMapping("/management/writer/edit/{writerSlug}")
    public String getWriterEdit(@PathVariable String writerSlug, Model model) {
        Writer writer = writerService.getBySlug(writerSlug);
        WriterCreateEditDto writerDto = writerService.getWriterCreateEditDto(writer);
        model.addAttribute(WRITER, writerDto);
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/literature/writer-edit";
    }

    @PutMapping("/management/writer/edit/{writerSlug}")
    public String putWriterEdit(@PathVariable String writerSlug,
                                @ModelAttribute(WRITER) WriterCreateEditDto writerDto) {
        String writerDtoSlug = writerService.updateWriter(writerSlug, writerDto).getSlug();
        return "redirect:/management/writer/edit/" + writerDtoSlug;
    }

    @PostMapping("/management/writer/edit/{writerSlug}/image/upload")
    public String postUploadWriterImage(@PathVariable String writerSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.getEncoder().encode(file.getBytes());
        writerService.updateWriterImage(writerSlug, bytes);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/writer/edit/{writerSlug}/image/delete")
    public String deleteWriterImage(@PathVariable String writerSlug) {
        writerService.deleteWriterImage(writerSlug);
        return "redirect:/management/writer/edit/" + writerSlug;
    }

    @DeleteMapping("/management/writer/delete/{writerSlug}")
    public String deleteWriterBySlug(@PathVariable String writerSlug) {
        writerService.deleteWriter(writerSlug);
        return "redirect:/management/writer/all";
    }
}
