package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/management/composition")
public class CompositionManagementController {

    @Autowired
    private CompositionService compositionService;

    @GetMapping("/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllCompositions();
        model.addAttribute("compositions", compositionService.getCompositionViewDTOList(allCompositions));
        return "management/composition-all";
    }

    @GetMapping("/create")
    public String getCreateComposition(Model model) {
        model.addAttribute("composition", new CompositionCreateEditDTO());
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-create";
    }

    @PostMapping("/create")
    public String postCreateComposition(@ModelAttribute("composition") CompositionCreateEditDTO compositionCreateEditDTO) {
        compositionService.createCompositionByCompositionDTO(compositionCreateEditDTO);
        return "redirect:/management/composition/all";
    }

    @GetMapping("/edit/{slug}")
    public String getEditCompositionBySlug(@PathVariable String slug, Model model) {
        Composition compositionBySlug = compositionService.getCompositionBySlug(slug);
        model.addAttribute("composition", compositionService.getCompositionCreateEditDTO(compositionBySlug));
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-edit";
    }

    @PutMapping("/edit/{slug}")
    public String putEditCompositionBySlug(@PathVariable String slug, @ModelAttribute("composition") CompositionCreateEditDTO compositionCreateEditDTO) {
        compositionService.updateExistingComposition(compositionCreateEditDTO, slug);
        return "redirect:/management/composition/all";
    }

    @DeleteMapping("/delete/{slug}")
    public String deleteCompositionBySlug(@PathVariable String slug, Model model) {
        compositionService.deleteCompositionBySlug(slug);
        model.addAttribute("compositions", compositionService.getAllCompositions());
        return "redirect:/management/composition/all";
    }
}
