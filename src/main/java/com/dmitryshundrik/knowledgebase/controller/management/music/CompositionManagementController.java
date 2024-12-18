package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.entity.music.Album;
import com.dmitryshundrik.knowledgebase.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.dto.music.AlbumSelectDTO;
import com.dmitryshundrik.knowledgebase.dto.music.CompositionCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
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
import javax.validation.Valid;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.COMPOSITION;
import static com.dmitryshundrik.knowledgebase.util.Constants.COMPOSITION_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_MUSIC_GENRES;

@Controller
@RequiredArgsConstructor
public class CompositionManagementController {

    private final CompositionService compositionService;

    private final AlbumService albumService;

    private final MusicianService musicianService;

    private final MusicGenreService musicGenreService;

    @GetMapping("/management/composition/all")
    public String getAllCompositions(Model model) {
        List<Composition> allCompositions = compositionService.getAllOrderedByCreatedDesc();
        model.addAttribute(COMPOSITION_LIST, compositionService
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
        model.addAttribute(COMPOSITION, compositionDTO);
        model.addAttribute(ALBUM_LIST, albumSelectDTOList);
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/composition-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String postCreateComposition(@PathVariable String musicianSlug,
                                        @Valid @ModelAttribute(COMPOSITION) CompositionCreateEditDTO compositionDTO,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<AlbumSelectDTO> albumDTOList = albumService
                    .getAlbumSelectDTOList(albumService
                            .getAllAlbumsByMusician(musicianService
                                    .getMusicianBySlug(musicianSlug)));
            musicianService.setFieldsToCompositionDTO(musicianSlug, compositionDTO);
            model.addAttribute(ALBUM_LIST, albumDTOList);
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
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
        model.addAttribute(COMPOSITION, compositionService.getCompositionCreateEditDTO(compositionBySlug));
        model.addAttribute(ALBUM_LIST, albumDTOList);
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/composition-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String putEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug,
                                           @ModelAttribute(COMPOSITION) CompositionCreateEditDTO compositionDTO) {
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
