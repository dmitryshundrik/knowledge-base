package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.*;
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

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;


    @GetMapping("/management/composition/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllCompositions();
        model.addAttribute("compositionViewDTOList", compositionService.getCompositionViewDTOList(allCompositions));
        return "management/composition-all";
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
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
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
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
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
    public String deleteMusiciansCompositionBySlug(@PathVariable String musicianSlug, @PathVariable String compositionSlug) {
        compositionService.deleteCompositionBySlug(compositionSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/composition/delete/{compositionSlug}")
    public String deleteCompositionBySlug(@PathVariable String compositionSlug) {
        compositionService.deleteCompositionBySlug(compositionSlug);
        return "redirect:/management/composition/all";
    }
}
