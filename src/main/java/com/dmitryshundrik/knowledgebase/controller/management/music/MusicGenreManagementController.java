package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicGenreCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping()
public class MusicGenreManagementController {

    @Autowired
    private MusicGenreService musicGenreService;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @GetMapping("/management/music-genre/all")
    public String getAllMusicGenres(Model model) {
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        return "management/music-genre-all";
    }

    @GetMapping("/management/music-genre/create")
    public String getCreateMusicGenre(Model model) {
        model.addAttribute("dto", new MusicGenreCreateEditDTO());
        model.addAttribute("musicGenreTypes", MusicGenreType.values());
        return "management/music-genre-create";
    }

    @PostMapping("/management/music-genre/create")
    public String postCreateMusicGenre(@Valid @ModelAttribute("dto") MusicGenreCreateEditDTO genreDTO, BindingResult bindingResult,
                                       Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("musicGenreTypes", MusicGenreType.values());
            return "management/music-genre-create";
        }
        String slug = musicGenreService.createMusicGenreByDTO(genreDTO);
        return "redirect:/management/music-genre/edit/" + slug;
    }

    @GetMapping("/management/music-genre/edit/{genreSlug}")
    public String getEditMusicGenre(@PathVariable String genreSlug, Model model) {
        MusicGenre musicGenreBySlug = musicGenreService.getMusicGenreBySlug(genreSlug);
        model.addAttribute("dto", musicGenreService.getMusicGenreCreateEditDTO(musicGenreBySlug));
        model.addAttribute("musicGenreTypes", MusicGenreType.values());
        return "management/music-genre-edit";
    }

    @PutMapping("management/music-genre/edit/{genreSlug}")
    public String putEditMusicGenre(@PathVariable String genreSlug, @ModelAttribute("dto") MusicGenreCreateEditDTO genreDTO) {
        String slug = musicGenreService.updateMusicGenre(genreSlug, genreDTO);
        return "redirect:/management/music-genre/edit/" + slug;
    }

    @DeleteMapping("/management/music-genre/delete/{genreSlug}")
    public String deleteMusicGenre(@PathVariable String genreSlug) {
        MusicGenre genre = musicGenreService.getMusicGenreBySlug(genreSlug);
        musicianService.getAllMusiciansByGenre(genre)
                .forEach(musician -> musician.getMusicGenres().remove(genre));
        albumService.getAllAlbumsByGenre(genre)
                .forEach(album -> album.getMusicGenres().remove(genre));
        compositionService.getAllCompositionsByGenre(genre)
                .forEach(composition -> composition.getMusicGenres().remove(genre));
        musicGenreService.deleteMusicGenre(genre);
        return "redirect:/management/music-genre/all";
    }
}
