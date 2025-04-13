package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.util.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.dto.music.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.util.enums.SortType;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
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
import javax.validation.Valid;
import java.io.IOException;

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
        model.addAttribute(MUSICIAN_LIST, musicianService.getAllMusicianManagementResponseDto());
        return "management/music/musician-archive";
    }

    @GetMapping("/management/musician/all-detailed")
    public String getAllMusiciansDetailed(Model model) {
        model.addAttribute(MUSICIAN_LIST, musicianService.getAllMusicianManagementDetailedResponseDto());
        return "management/music/musician-archive-detailed";
    }

    @GetMapping("/management/musician/create")
    public String getCreateMusician(Model model) {
        MusicianCreateEditDTO musicianDTO = new MusicianCreateEditDTO();
        musicianDTO.setAlbumsSortType(SortType.YEAR);
        musicianDTO.setCompositionsSortType(SortType.YEAR);
        model.addAttribute(MUSICIAN, musicianDTO);
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllSortedByStart());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
        model.addAttribute(SORT_TYPE_LIST, SortType.values());
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/music/musician-create";
    }

    @PostMapping("/management/musician/create")
    public String postCreateMusician(@Valid @ModelAttribute(MUSICIAN) MusicianCreateEditDTO musicianDTO,
                                     BindingResult bindingResult, Model model) {
        String error = musicianService.musicianSlugIsExist(musicianDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllSortedByStart());
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
            model.addAttribute(SORT_TYPE_LIST, SortType.values());
            model.addAttribute(GENDER_LIST, Gender.values());
            return "management/music/musician-create";
        }
        String musicianDTOSlug = musicianService.createMusician(musicianDTO).getSlug();
        return "redirect:/management/musician/edit/" + musicianDTOSlug;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}")
    public String getEditMusicianBySlug(@PathVariable String musicianSlug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(musicianSlug);
        model.addAttribute(MUSICIAN, musicianService.getMusicianCreateEditDTO(musicianBySlug));
        model.addAttribute(MUSIC_PERIOD_LIST, musicPeriodService.getAllSortedByStart());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenres());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenres());
        model.addAttribute(SORT_TYPE_LIST, SortType.values());
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/music/musician-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}")
    public String putEditMusicianBySlug(@PathVariable String musicianSlug,
                                        @ModelAttribute(MUSICIAN) MusicianCreateEditDTO musicianDTO) {
        String musicianDTOSlug = musicianService.updateMusician(musicianSlug, musicianDTO).getSlug();
        return "redirect:/management/musician/edit/" + musicianDTOSlug;
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/image/upload")
    public String postUploadMusicianImage(@PathVariable String musicianSlug,
                                          @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        musicianService.updateMusicianImageBySlug(musicianSlug, bytes);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/edit/{musicianSlug}/image/delete")
    public String deleteMusicianImage(@PathVariable String musicianSlug) {
        musicianService.deleteMusicianImage(musicianSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("/management/musician/delete/{musicianSlug}")
    public String deleteMusicianBySlug(@PathVariable String musicianSlug) {
        musicianService.deleteMusicianBySlug(musicianSlug);
        return "redirect:/management/musician/all";
    }
}
