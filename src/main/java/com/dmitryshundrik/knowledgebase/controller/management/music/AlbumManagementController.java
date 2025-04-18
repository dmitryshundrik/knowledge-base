package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.MusicGenreService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.util.MusicianDtoTransformer;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM;
import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM_COLLABORATORS;
import static com.dmitryshundrik.knowledgebase.util.Constants.ALBUM_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.CLASSICAL_MUSIC_GENRES;
import static com.dmitryshundrik.knowledgebase.util.Constants.CONTEMPORARY_MUSIC_GENRES;

@Controller
@RequiredArgsConstructor
public class AlbumManagementController {

    private final AlbumService albumService;

    private final MusicianService musicianService;

    private final MusicGenreService musicGenreService;

    @GetMapping("/management/album/all")
    public String getAllAlbums(Model model) {
        List<Album> sortedAlbums = albumService.getAllAlbumsSortedByCreatedDesc();
        model.addAttribute(ALBUM_LIST, albumService.getAlbumViewDTOList(sortedAlbums));
        return "management/music/album-all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String getCreateAlbum(@PathVariable String musicianSlug, Model model) {
        AlbumCreateEditDTO albumDTO = new AlbumCreateEditDTO();
        musicianService.setFieldsToAlbumDto(musicianSlug, albumDTO);
        model.addAttribute(ALBUM, albumDTO);
        model.addAttribute(ALBUM_COLLABORATORS, MusicianDtoTransformer
                .getMusicianSelectDtoList(musicianService.getAll()));
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/album-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String postCreateAlbum(@PathVariable String musicianSlug,
                                  @Valid @ModelAttribute(ALBUM) AlbumCreateEditDTO albumDTO,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            musicianService.setFieldsToAlbumDto(musicianSlug, albumDTO);
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
            return "management/music/album-create";
        }
        String albumDTOSlug = albumService
                .createAlbum(albumDTO, musicianService.getMusicianBySlug(musicianSlug),
                        musicianService.getAllMusiciansByUUIDList(albumDTO.getCollaboratorsUUID())).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + albumDTOSlug;
    }

    @GetMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String getEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug, Model model) {
        Album albumBySlug = albumService.getAlbumBySlug(albumSlug);
        model.addAttribute(ALBUM, albumService.getAlbumCreateEditDTO(albumBySlug));
        model.addAttribute(ALBUM_COLLABORATORS, MusicianDtoTransformer
                .getMusicianSelectDtoList(musicianService.getAll()));
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresSortedByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresSortedByTitle());
        return "management/music/album-edit";
    }

    @PutMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String putEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug,
                                     @ModelAttribute(ALBUM) AlbumCreateEditDTO albumDTO) {
        String albumDTOSlug = albumService.updateAlbum(albumSlug, albumDTO,
                musicianService.getAllMusiciansByUUIDList(albumDTO.getCollaboratorsUUID())).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + albumDTOSlug;
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
