package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.entity.art.Artist;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistCreateEditDto;
import com.dmitryshundrik.knowledgebase.model.dto.art.ArtistViewDto;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
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
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.ARTIST_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.GENDER_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class ArtistManagementController {

    private final ArtistService artistService;

    @GetMapping("/management/artist/all")
    public String getAllArtists(Model model) {
        List<Artist> artistList = artistService.getAllOrderByCreatedDesc();
        List<ArtistViewDto> artistDtoList = artistService.getArtistViewDtoList(artistList);
        model.addAttribute(ARTIST_LIST, artistDtoList);
        return "management/art/artist-archive";
    }

    @GetMapping("/management/artist/create")
    public String getArtistCreate(Model model) {
        ArtistCreateEditDto artistDto = new ArtistCreateEditDto();
        model.addAttribute(ARTIST, artistDto);
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/art/artist-create";
    }

    @PostMapping("/management/artist/create")
    public String postArtistCreate(@Valid @ModelAttribute(ARTIST) ArtistCreateEditDto artistDto,
                                    BindingResult bindingResult, Model model) {
        String error = artistService.isSlugExists(artistDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            model.addAttribute(GENDER_LIST, Gender.values());
            return "management/art/artist-create";
        }
        String artistSlug = artistService.createArtist(artistDto).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}")
    public String getArtistEdit(@PathVariable String artistSlug, Model model) {
        Artist artist = artistService.getBySlug(artistSlug);
        ArtistCreateEditDto artistDto = artistService.getArtistCreateEditDto(artist);
        model.addAttribute(ARTIST, artistDto);
        model.addAttribute(GENDER_LIST, Gender.values());
        return "management/art/artist-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}")
    public String putArtistEdit(@PathVariable String artistSlug,
                                 @ModelAttribute(ARTIST) ArtistCreateEditDto artistDto) {
        String artistDtoSlug = artistService.updateArtist(artistSlug, artistDto).getSlug();
        return "redirect:/management/artist/edit/" + artistDtoSlug;
    }

    @PostMapping("/management/artist/edit/{artistSlug}/image/upload")
    public String postUploadArtistImage(@PathVariable String artistSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.getEncoder().encode(file.getBytes());
        artistService.updateArtistImageBySlug(artistSlug, bytes);
        return "redirect:/management/artist/edit/" + artistSlug;
    }

    @DeleteMapping("/management/artist/edit/{artistSlug}/image/delete")
    public String deleteArtistImage(@PathVariable String artistSlug) {
        artistService.deleteArtistImage(artistSlug);
        return "redirect:/management/artist/edit/" + artistSlug;
    }

    @DeleteMapping("/management/artist/delete/{artistSlug}")
    public String deleteArtistBySlug(@PathVariable String artistSlug) {
        artistService.deleteArtist(artistSlug);
        return "redirect:/management/artist/all";
    }
}
