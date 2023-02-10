package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class CompositionManagementController {

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/composition/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllCompositions();
        model.addAttribute("compositionViewDTOList", compositionService.getCompositionViewDTOList(allCompositions));
        return "management/composition-all";
    }

    @GetMapping("/management/musician/edit/{slug}/composition/create")
    public String getCreateComposition(@PathVariable String slug, Model model) {
        CompositionCreateEditDTO compositionCreateEditDTO = new CompositionCreateEditDTO();
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        compositionCreateEditDTO.setMusicianNickname(musicianBySlug.getNickName());
        compositionCreateEditDTO.setMusicianSlug(musicianBySlug.getSlug());
        model.addAttribute("compositionCreateEditDTO", compositionCreateEditDTO);
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-create";
    }

    @PostMapping("/management/musician/edit/{slug}/composition/create")
    public String postCreateComposition(@PathVariable String slug, @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionCreateEditDTO) {
        compositionService.createCompositionByCompositionDTO(compositionCreateEditDTO, musicianService.getMusicianBySlug(slug));
        return "redirect:/management/musician/edit/" + slug;
    }

    @GetMapping("/management/musician/edit/{slugMus}/composition/edit/{slugCom}")
    public String getEditCompositionBySlug(@PathVariable String slugMus, @PathVariable String slugCom, Model model) {
        Composition compositionBySlug = compositionService.getCompositionBySlug(slugCom);
        model.addAttribute("compositionCreateEditDTO", compositionService.getCompositionCreateEditDTO(compositionBySlug));
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-edit";
    }

    @PutMapping("/management/musician/edit/{slugMus}/composition/edit/{slugCom}")
    public String putEditCompositionBySlug(@PathVariable String slugMus, @PathVariable String slugCom, @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionCreateEditDTO) {
        compositionService.updateExistingComposition(compositionCreateEditDTO, slugCom);
        return "redirect:/management/musician/edit/" + slugMus;
    }

    @DeleteMapping("/management/musician/edit/{slugMus}/composition/delete/{slugCom}")
    public String deleteCompositionBySlug(@PathVariable String slugMus, @PathVariable String slugCom) {
        compositionService.deleteCompositionBySlug(slugCom);
        return "redirect:/management/musician/edit/" + slugMus;
    }
}
