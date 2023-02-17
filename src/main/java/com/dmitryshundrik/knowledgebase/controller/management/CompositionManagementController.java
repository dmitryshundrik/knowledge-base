package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping()
public class CompositionManagementController {

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/composition/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllCompositions();
        model.addAttribute("compositionViewDTOList", compositionService.getCompositionViewDTOList(allCompositions));
        return "management/composition-all";
    }

    @DeleteMapping("/management/composition/delete/{compositionSlug}")
    public String deleteCompositionBySlug(@PathVariable String compositionSlug) {
        compositionService.deleteCompositionBySlug(compositionSlug);
        return "redirect:/management/composition/all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String getCreateComposition(@PathVariable String musicianSlug, Model model) {
        CompositionCreateEditDTO compositionCreateEditDTO = new CompositionCreateEditDTO();
        musicianService.setMusicianFieldsToCompositionDTO(compositionCreateEditDTO, musicianSlug);
        List<Album> allAlbumsByMusician = albumService.
                getAllAlbumsByMusician(musicianService.getMusicianBySlug(musicianSlug));
        List<AlbumSelectDTO> albumSelectDTOList = albumService.getAlbumSelectDTOList(allAlbumsByMusician);
        model.addAttribute("compositionCreateEditDTO", compositionCreateEditDTO);
        model.addAttribute("albumSelectDTOList", albumSelectDTOList);
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String postCreateComposition(@PathVariable String musicianSlug,
                                        @Valid @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionCreateEditDTO,
                                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            musicianService.setMusicianFieldsToCompositionDTO(compositionCreateEditDTO, musicianSlug);
            return "management/composition-create";
        }
        Musician musicianBySlug = musicianService.getMusicianBySlug(musicianSlug);
        Album albumByAlbumId = albumService.preparingAlbumById(compositionCreateEditDTO);
        CompositionCreateEditDTO DTOByCreatedComposition = compositionService
                .createCompositionByDTO(compositionCreateEditDTO, musicianBySlug, albumByAlbumId);
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + DTOByCreatedComposition.getSlug();
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String getEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug, Model model) {
        Composition compositionBySlug = compositionService.getCompositionBySlug(compositionSlug);
        List<Album> allAlbumsByMusician = albumService
                .getAllAlbumsByMusician(musicianService.getMusicianBySlug(musicianSlug));
        List<AlbumSelectDTO> albumSelectDTOList = albumService.getAlbumSelectDTOList(allAlbumsByMusician);
        model.addAttribute("compositionCreateEditDTO", compositionService.getCompositionCreateEditDTO(compositionBySlug));
        model.addAttribute("albumSelectDTOList", albumSelectDTOList);
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporaryGenres", ContemporaryGenre.getSortedValues());
        return "management/composition-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String putEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug,
                                           @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionCreateEditDTO) {
        Album albumByAlbumId = albumService.preparingAlbumById(compositionCreateEditDTO);
        compositionService.updateExistingComposition(compositionCreateEditDTO, compositionSlug, albumByAlbumId);
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + compositionCreateEditDTO.getSlug();
    }

    @DeleteMapping("/management/musician/edit/{musicianSlug}/composition/delete/{compositionSlug}")
    public String deleteCompositionBySlug(@PathVariable String musicianSlug, @PathVariable String compositionSlug) {
        compositionService.deleteCompositionBySlug(compositionSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }
}
