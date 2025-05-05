package com.dmitryshundrik.knowledgebase.controller.management.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.dto.music.AlbumCreateEditDto;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
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
        List<Album> albumList = albumService.getAllOrderByCreatedDesc();
        model.addAttribute(ALBUM_LIST, albumService.getAlbumViewDtoList(albumList));
        return "management/music/album-all";
    }

    @GetMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String getCreateAlbum(@PathVariable String musicianSlug, Model model) {
        AlbumCreateEditDto albumDto = new AlbumCreateEditDto();
        musicianService.setFieldsToAlbumDto(musicianSlug, albumDto);
        model.addAttribute(ALBUM, albumDto);
        model.addAttribute(ALBUM_COLLABORATORS, musicianService.getAllMusicianSelectDto());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
        return "management/music/album-create";
    }

    @PostMapping("/management/musician/edit/{musicianSlug}/album/create")
    public String postCreateAlbum(@PathVariable String musicianSlug,
                                  @Valid @ModelAttribute(ALBUM) AlbumCreateEditDto albumDto,
                                  BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            musicianService.setFieldsToAlbumDto(musicianSlug, albumDto);
            model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
            model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
            return "management/music/album-create";
        }
        String albumDtoSlug = albumService
                .createAlbum(albumDto, musicianService.getBySlug(musicianSlug),
                        musicianService.getAllByUUIDList(albumDto.getCollaboratorsUUID())).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + albumDtoSlug;
    }

    @GetMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String getEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug, Model model) {
        Album albumBySlug = albumService.getBySlug(albumSlug);
        model.addAttribute(ALBUM, albumService.getAlbumCreateEditDto(albumBySlug));
        model.addAttribute(ALBUM_COLLABORATORS, musicianService.getAllMusicianSelectDto());
        model.addAttribute(CLASSICAL_MUSIC_GENRES, musicGenreService.getAllClassicalGenresOrderByTitle());
        model.addAttribute(CONTEMPORARY_MUSIC_GENRES, musicGenreService.getAllContemporaryGenresOrderByTitle());
        return "management/music/album-edit";
    }

    @PutMapping("management/musician/edit/{musicianSlug}/album/edit/{albumSlug}")
    public String putEditAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug,
                                     @ModelAttribute(ALBUM) AlbumCreateEditDto albumDTO) {
        String albumDtoSlug = albumService.updateAlbum(albumSlug, albumDTO,
                musicianService.getAllByUUIDList(albumDTO.getCollaboratorsUUID())).getSlug();
        return "redirect:/management/musician/edit/" + musicianSlug + "/album/edit/" + albumDtoSlug;
    }

    @DeleteMapping("management/musician/edit/{musicianSlug}/album/delete/{albumSlug}")
    public String deleteMusiciansAlbumBySlug(@PathVariable String musicianSlug, @PathVariable String albumSlug) {
        Album albumBySlug = albumService.getBySlug(albumSlug);
        albumBySlug.getCompositions().forEach(composition -> composition.setAlbum(null));
        albumService.deleteAlbum(albumSlug);
        return "redirect:/management/musician/edit/" + musicianSlug;
    }

    @DeleteMapping("management/album/delete/{albumSlug}")
    public String deleteAlbumBySlug(@PathVariable String albumSlug) {
        Album albumBySlug = albumService.getBySlug(albumSlug);
        albumBySlug.getCompositions().forEach(composition -> composition.setAlbum(null));
        albumService.deleteAlbum(albumSlug);
        return "redirect:/management/album/all";
    }
}
