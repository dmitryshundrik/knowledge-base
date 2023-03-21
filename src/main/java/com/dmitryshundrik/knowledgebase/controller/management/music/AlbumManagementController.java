package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicPeriodService;
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
public class AlbumManagementController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private MusicPeriodService musicPeriodService;

    @Autowired
    private MusicGenreService musicGenreService;

    @GetMapping("/management/album/all")
    public String getAllAlbums(Model model) {
        List<Album> sortedAlbums = albumService.getAllAlbumsSortedByCreated();
        model.addAttribute("albumViewDTOList", albumService.getAlbumViewDTOList(sortedAlbums));
        return "management/album-all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String getCreateAlbum(@PathVariable String musicianSlug, Model model) {
        AlbumCreateEditDTO albumDTO = new AlbumCreateEditDTO();
        musicianService.setMusicianFieldsToAlbumDTO(albumDTO, musicianSlug);
        model.addAttribute("albumCreateEditDTO", albumDTO);
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        return "management/album-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String postCreateAlbum(@PathVariable String musicianSlug,
                                  @Valid @ModelAttribute("albumCreateEditDTO") AlbumCreateEditDTO albumDTO,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            musicianService.setMusicianFieldsToAlbumDTO(albumDTO, musicianSlug);
            return "management/album-create";
        }
        AlbumCreateEditDTO DTOByCreated = albumService
                .createAlbumByDTO(albumDTO, musicianService.getMusicianBySlug(musicianSlug));
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + DTOByCreated.getSlug();
    }

    @GetMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String getEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug, Model model) {
        Album albumBySlug = albumService.getAlbumBySlug(albumSlug);
        model.addAttribute("albumCreateEditDTO", albumService.getAlbumCreateEditDTO(albumBySlug));
        model.addAttribute("musicPeriods", musicPeriodService.getAll());
        model.addAttribute("classicalGenres", musicGenreService.getAllClassicalGenres());
        model.addAttribute("contemporaryGenres", musicGenreService.getAllContemporaryGenres());
        return "management/album-edit";
    }

    @PutMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String putEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug,
                                     @ModelAttribute("albumCreateEditDTO") AlbumCreateEditDTO albumDTO) {
        albumService.updateExistingAlbum(albumDTO, albumSlug);
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + albumDTO.getSlug();
    }

    @DeleteMapping("management/musician/edit/{musicianSlug}/album/delete/{albumSlug}")
    public String deleteMusiciansAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug) {
        Album albumBySlug = albumService.getAlbumBySlug(albumSlug);
        albumBySlug.getCompositions().forEach(composition -> composition.setAlbum(null));
        albumService.deleteAlbumBySlug(albumSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("management/album/delete/{albumSlug}")
    public String deleteAlbumBySlug(@PathVariable String albumSlug) {
        Album albumBySlug = albumService.getAlbumBySlug(albumSlug);
        albumBySlug.getCompositions().forEach(composition -> composition.setAlbum(null));
        albumService.deleteAlbumBySlug(albumSlug);
        return "redirect:/management/album/all";
    }
}
