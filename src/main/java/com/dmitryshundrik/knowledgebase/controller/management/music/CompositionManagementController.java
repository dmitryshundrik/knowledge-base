package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.model.music.dto.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Controller
public class CompositionManagementController {

    private final CompositionService compositionService;

    private final AlbumService albumService;

    private final MusicianService musicianService;

    private final MusicGenreService musicGenreService;

    public CompositionManagementController(CompositionService compositionService, AlbumService albumService,
                                           MusicianService musicianService, MusicGenreService musicGenreService) {
        this.compositionService = compositionService;
        this.albumService = albumService;
        this.musicianService = musicianService;
        this.musicGenreService = musicGenreService;
    }


    @GetMapping("/management/composition/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllOrderedByCreatedDesc();
        model.addAttribute("compositionViewDTOList", compositionService
                .getCompositionViewDTOList(allCompositions));
        return "management/music/composition-all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String getCreateComposition(@PathVariable String musicianSlug, Model model) {
        CompositionCreateEditDTO compositionDTO = new CompositionCreateEditDTO();
        musicianService.setFieldsToCompositionDTO(musicianSlug, compositionDTO);
        List<AlbumSelectDTO> albumSelectDTOList = albumService
                .getAlbumSelectDTOList(albumService
                        .getAllAlbumsByMusician(musicianService
                                .getMusicianBySlug(musicianSlug)));
        model.addAttribute("compositionCreateEditDTO", compositionDTO);
        model.addAttribute("albumSelectDTOList", albumSelectDTOList);
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/composition-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String postCreateComposition(@PathVariable String musicianSlug,
                                        @Valid @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionDTO,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<AlbumSelectDTO> albumDTOList = albumService
                    .getAlbumSelectDTOList(albumService
                            .getAllAlbumsByMusician(musicianService
                                    .getMusicianBySlug(musicianSlug)));
            musicianService.setFieldsToCompositionDTO(musicianSlug, compositionDTO);
            model.addAttribute("albumSelectDTOList", albumDTOList);
            model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenresSortedByTitle());
            model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenresSortedByTitle());
            return "management/music/composition-create";
        }
        Musician musicianBySlug = musicianService.getMusicianBySlug(musicianSlug);
        Album albumById = (!compositionDTO.getAlbumId().isBlank() ? albumService.getAlbumById(compositionDTO.getAlbumId()) : null);
        String compositionDTOSlug = compositionService.createComposition(compositionDTO, musicianBySlug, albumById).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + compositionDTOSlug;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String getEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug, Model model) {
        Composition compositionBySlug = compositionService.getCompositionBySlug(compositionSlug);
        List<AlbumSelectDTO> albumDTOList = albumService
                .getAlbumSelectDTOList(albumService
                        .getAllAlbumsByMusician(musicianService
                                .getMusicianBySlug(musicianSlug)));
        model.addAttribute("compositionCreateEditDTO", compositionService.getCompositionCreateEditDTO(compositionBySlug));
        model.addAttribute("albumSelectDTOList", albumDTOList);
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/composition-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String putEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug,
                                           @ModelAttribute("compositionCreateEditDTO") CompositionCreateEditDTO compositionDTO) {
        Album albumById = (!compositionDTO.getAlbumId().isBlank() ? albumService.getAlbumById(compositionDTO.getAlbumId()) : null);
        String compositionDTOSlug = compositionService.updateComposition(compositionDTO, compositionSlug, albumById).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + compositionDTOSlug;
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
