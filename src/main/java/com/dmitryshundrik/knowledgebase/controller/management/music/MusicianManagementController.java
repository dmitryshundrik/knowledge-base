package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.MusicianCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicianManagementController {

    private final MusicianService musicianService;

    private final MusicPeriodService musicPeriodService;

    private final MusicGenreService musicGenreService;

    @GetMapping("/management/musician/all")
    public String getAllMusicians(Model model) {
        List<Musician> allMusicians = musicianService.getAllMusiciansOrderedByCreatedDesc();
        model.addAttribute("musicianViewDTOList", musicianService.getMusicianViewDTOList(allMusicians));
        return "management/music/musician-all";
    }

    @GetMapping("/management/musician/create")
    public String getCreateMusician(Model model) {
        MusicianCreateEditDTO musicianDTO = new MusicianCreateEditDTO();
        musicianDTO.setAlbumsSortType(SortType.YEAR);
        musicianDTO.setCompositionsSortType(SortType.YEAR);
        model.addAttribute("musicianCreateEditDTO", musicianDTO);
        model.addAttribute("musicPeriods", musicPeriodService.getAllSortedByStart());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        model.addAttribute("sortTypes", SortType.values());
        model.addAttribute("genders", Gender.values());
        return "management/music/musician-create";
    }

    @PostMapping("/management/musician/create")
    public String postCreateMusician(@Valid @ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianDTO,
                                     BindingResult bindingResult, Model model) {
        String error = musicianService.musicianSlugIsExist(musicianDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            model.addAttribute("musicPeriods", musicPeriodService.getAllSortedByStart());
            model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
            model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
            model.addAttribute("sortTypes", SortType.values());
            model.addAttribute("genders", Gender.values());
            return "management/music/musician-create";
        }
        String musicianDTOSlug = musicianService.createMusician(musicianDTO).getSlug();
        return "redirect:/management/musician/edit/" + musicianDTOSlug;
    }

    @GetMapping("/management/musician/edit/{musicianSlug}")
    public String getEditMusicianBySlug(@PathVariable String musicianSlug, Model model) {
        Musician musicianBySlug = musicianService.getMusicianBySlug(musicianSlug);
        model.addAttribute("musicianCreateEditDTO", musicianService.getMusicianCreateEditDTO(musicianBySlug));
        model.addAttribute("musicPeriods", musicPeriodService.getAllSortedByStart());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        model.addAttribute("sortTypes", SortType.values());
        model.addAttribute("genders", Gender.values());
        return "management/music/musician-edit";
    }

    @PutMapping("/management/musician/edit/{musicianSlug}")
    public String putEditMusicianBySlug(@PathVariable String musicianSlug,
                                        @ModelAttribute("musicianCreateEditDTO") MusicianCreateEditDTO musicianDTO) {
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
