package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Base64;

import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.GENDER_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSICIAN_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.MUSIC_PERIOD_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;
import static com.dmitryshundrik.knowledgebase.util.Constants.SORT_TYPE_LIST;

@Controller
@RequiredArgsConstructor
public class MusicianManagementController {

    private final MusicianService musicianService;

    private final MusicPeriodService musicPeriodService;

    private final MusicGenreService musicGenreService;

    @GetMapping("/management/musician/all")
    public String getAllMusicians(Model model) {
        model.addAttribute(MUSICIAN_LIST, musicianService.getAllMusicianArchiveDto());
        return "management/music/musician-archive";
    }

    @GetMapping("/management/musician/all-detailed")
    public String getAllMusiciansDetailed(Model model) {
        model.addAttribute(MUSICIAN_LIST, musicianService.getAllMusicianArchiveDetailedDto());
        return "management/music/musician-archive-detailed";
    }

    @GetMapping("/management/musician/create")
    public String getCreateMusician(Model model) {
        MusicianCreateEditDto musicianDto = new MusicianCreateEditDto();
        musicianDto.setAlbumsSortType(SortType.YEAR);
        musicianDto.setCompositionsSortType(SortType.YEAR);
        model.addAttribute(MUSICIAN, musicianDto);
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllOrderByStart());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
        model.addAttribute(SORT_TYPE_LIST, SortType.values());
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/music/musician-create";
    }

    @PostMapping("/management/musician/create")
    public String postCreateMusician(@Valid @ModelAttribute(MUSICIAN) MusicianCreateEditDto musicianDto,
                                     BindingResult bindingResult, Model model) {
        String error = musicianService.isSlugExists(musicianDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllOrderByStart());
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
            model.addAttribute(SORT_TYPE_LIST, SortType.values());
            model.addAttribute(GENDER_LIST, Gender.values());
            return "management/music/musician-create";
        }
        String musicianDTOSlug = musicianService.createMusician(musicianDto).getSlug();
        return "redirect:/management/musician/edit/" + musicianDTOSlug;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}")
    public String getEditMusicianBySlug(@PathVariable String musicianSlug, Model model) {
        Musician musicianBySlug = musicianService.getBySlug(musicianSlug);
        model.addAttribute(MUSICIAN, musicianService.getMusicianCreateEditDto(musicianBySlug));
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllOrderByStart());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
        model.addAttribute(SORT_TYPE_LIST, SortType.values());
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/music/musician-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}")
    public String putEditMusicianBySlug(@PathVariable String musicianSlug,
                                        @ModelAttribute(MUSICIAN) MusicianCreateEditDto musicianDto) {
        String musicianDtoSlug = musicianService.updateMusician(musicianSlug, musicianDto).getSlug();
        return "redirect:/management/musician/edit/" + musicianDtoSlug;
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/image/upload")
    public String postUploadMusicianImage(@PathVariable String musicianSlug,
                                          @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.getEncoder().encode(file.getBytes());
        musicianService.updateMusicianImage(musicianSlug, bytes);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/edit/{musicianSlug}/image/delete")
    public String deleteMusicianImage(@PathVariable String musicianSlug) {
        musicianService.deleteMusicianImage(musicianSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/delete/{musicianSlug}")
    public String deleteMusicianBySlug(@PathVariable String musicianSlug) {
        musicianService.deleteMusician(musicianSlug);
        return "redirect:/management/musician/all";
    }
}
