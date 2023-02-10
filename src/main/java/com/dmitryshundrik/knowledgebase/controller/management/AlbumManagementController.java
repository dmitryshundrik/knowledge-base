package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.music.dto.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping()
public class AlbumManagementController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MusicianService musicianService;

    @GetMapping("/management/album/all")
    public String getAllAlbums(Model model) {
        List<Album> allAlbums = albumService.getAllAlbums();
        model.addAttribute("albumViewDTOList", albumService.getAlbumViewDTOList(allAlbums));
        return "management/album-all";
    }

    @GetMapping("/management/musician/edit/{slug}/album/create")
    public String getCreateAlbum(@PathVariable String slug, Model model) {
        AlbumCreateEditDTO albumCreateEditDTO = new AlbumCreateEditDTO();
        Musician musicianBySlug = musicianService.getMusicianBySlug(slug);
        albumCreateEditDTO.setMusicianNickname(musicianBySlug.getNickName());
        albumCreateEditDTO.setMusicianSlug(musicianBySlug.getSlug());
        model.addAttribute("albumCreateEditDTO", albumCreateEditDTO);
        model.addAttribute("periods", Period.values());
        model.addAttribute("academicGenres", AcademicGenre.getSortedValues());
        model.addAttribute("contemporatyGenres", ContemporaryGenre.getSortedValues());
        return "management/album-create";
    }

    @PostMapping("/management/musician/edit/{slug}/album/create")
    public String postCreateAlbum(@PathVariable String slug, @ModelAttribute("albumCreateEditDTO") AlbumCreateEditDTO albumCreateEditDTO) {
        albumService.createAlbumByAlbumDTO(albumCreateEditDTO, musicianService.getMusicianBySlug(slug));
        return "redirect:/management/musician/edit/" + slug;
    }

}
