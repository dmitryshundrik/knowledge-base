package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicGenreCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_GENRE;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_GENRE_TYPE_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class MusicGenreManagementController {

    private final MusicGenreService musicGenreService;

    private final AlbumService albumService;

    private final CompositionService compositionService;

    @GetMapping("/management/music-genre/all")
    public String getAllMusicGenres(Model model) {
        List<MusicGenre> classicalGenresList = musicGenreService.getAllClassicalGenresOrderByTitle();
        List<MusicGenre> contemporaryGenresList = musicGenreService.getAllContemporaryGenresOrderByTitle();
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getMusicGenreViewDtoList(classicalGenresList));
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getMusicGenreViewDtoList(contemporaryGenresList));
        return "management/music/music-genre-all";
    }

    @GetMapping("/management/music-genre/create")
    public String getCreateMusicGenre(Model model) {
        model.addAttribute(MUSIC_GENRE, new MusicGenreCreateEditDto());
        model.addAttribute(MUSIC_GENRE_TYPE_LIST, MusicGenreType.values());
        return "management/music/music-genre-create";
    }

    @PostMapping("/management/music-genre/create")
    public String postCreateMusicGenre(@Valid @ModelAttribute(MUSIC_GENRE) MusicGenreCreateEditDto genreDto, BindingResult bindingResult,
                                       Model model) {
        String error = musicGenreService.isSlugExists(genreDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(MUSIC_GENRE_TYPE_LIST, MusicGenreType.values());
            return "management/music/music-genre-create";
        }
        String slug = musicGenreService.createMusicGenre(genreDto);
        return "redirect:/management/music-genre/edit/" + slug;
    }

    @GetMapping("/management/music-genre/edit/{genreSlug}")
    public String getEditMusicGenre(@PathVariable String genreSlug, Model model) {
        MusicGenre musicGenreBySlug = musicGenreService.getBySlug(genreSlug);
        model.addAttribute(MUSIC_GENRE, musicGenreService.getMusicGenreCreateEditDto(musicGenreBySlug));
        model.addAttribute(MUSIC_GENRE_TYPE_LIST, MusicGenreType.values());
        return "management/music/music-genre-edit";
    }

    @PutMapping("management/music-genre/edit/{genreSlug}")
    public String putEditMusicGenre(@PathVariable String genreSlug, @ModelAttribute(MUSIC_GENRE) MusicGenreCreateEditDto genreDto) {
        String slug = musicGenreService.updateMusicGenre(genreSlug, genreDto);
        return "redirect:/management/music-genre/edit/" + slug;
    }

    @DeleteMapping("/management/music-genre/delete/{genreSlug}")
    public String deleteMusicGenre(@PathVariable String genreSlug) {
        MusicGenre genre = musicGenreService.getBySlug(genreSlug);
        albumService.getAllByGenre(genre)
                .forEach(album -> album.getMusicGenres().remove(genre));
        compositionService.getAllByGenre(genre)
                .forEach(composition -> composition.getMusicGenres().remove(genre));
        musicGenreService.deleteMusicGenre(genre);
        return "redirect:/management/music-genre/all";
    }
}
