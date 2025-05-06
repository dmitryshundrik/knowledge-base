package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumSelectDto;
import com.dmitryshundrik.knowledgebase.model.dto.music.CompositionCreateEditDto;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
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
        List<Composition> compositionList = compositionService.getAllOrderByCreated();
        model.addAttribute(COMPOSITION_LIST, compositionService
                .getCompositionViewDtoList(compositionList));
        return "management/music/composition-all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String getCreateComposition(@PathVariable String musicianSlug, Model model) {
        CompositionCreateEditDto compositionDto = new CompositionCreateEditDto();
        musicianService.setFieldsToCompositionDto(musicianSlug, compositionDto);
        List<AlbumSelectDto> albumDtoList = albumService
                .getAlbumSelectDtoList(albumService
                        .getAllByMusician(musicianService
                                .getBySlug(musicianSlug)));
        model.addAttribute(COMPOSITION, compositionDto);
        model.addAttribute(ALBUM_LIST, albumDtoList);
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
        return "management/music/composition-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/composition/create")
    public String postCreateComposition(@PathVariable String musicianSlug,
                                        @Valid @ModelAttribute(COMPOSITION) CompositionCreateEditDto compositionDto,
                                        BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            List<AlbumSelectDto> albumDtoList = albumService
                    .getAlbumSelectDtoList(albumService
                            .getAllByMusician(musicianService
                                    .getBySlug(musicianSlug)));
            musicianService.setFieldsToCompositionDto(musicianSlug, compositionDto);
            model.addAttribute(ALBUM_LIST, albumDtoList);
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
            return "management/music/composition-create";
        }
        Musician musician = musicianService.getBySlug(musicianSlug);
        Album album = (!compositionDto.getAlbumId().isBlank() ? albumService.getById(compositionDto.getAlbumId()) : null);
        String compositionDtoSlug = compositionService.createComposition(compositionDto, musician, album).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + compositionDtoSlug;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String getEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug, Model model) {
        Composition composition = compositionService.getBySlug(compositionSlug);
        List<AlbumSelectDto> albumDtoList = albumService
                .getAlbumSelectDtoList(albumService
                        .getAllByMusician(musicianService
                                .getBySlug(musicianSlug)));
        model.addAttribute(COMPOSITION, compositionService.getCompositionCreateEditDto(composition));
        model.addAttribute(ALBUM_LIST, albumDtoList);
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
        return "management/music/composition-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}/composition/edit/{compositionSlug}")
    public String putEditCompositionBySlug(@PathVariable String musicianSlug,
                                           @PathVariable String compositionSlug,
                                           @ModelAttribute(COMPOSITION) CompositionCreateEditDto compositionDto) {
        Album album = (!compositionDto.getAlbumId().isBlank() ? albumService.getById(compositionDto.getAlbumId()) : null);
        String compositionDtoSlug = compositionService.updateComposition(compositionDto, compositionSlug, album).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/composition/edit/" + compositionDtoSlug;
    }

    @DeleteMapping("/management/musician/edit/{musicianSlug}/composition/delete/{compositionSlug}")
    public String deleteMusiciansCompositionBySlug(@PathVariable String musicianSlug, @PathVariable String compositionSlug) {
        compositionService.deleteComposition(compositionSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/composition/delete/{compositionSlug}")
    public String deleteCompositionBySlug(@PathVariable String compositionSlug) {
        compositionService.deleteComposition(compositionSlug);
        return "redirect:/management/composition/all";
    }
}
