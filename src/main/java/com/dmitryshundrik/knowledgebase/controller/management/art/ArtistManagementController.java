package com.dmitryshundrik.knowledgebase.controller.management.art;

import com.dmitryshundrik.knowledgebase.model.art.Artist;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistCreateEditDTO;
import com.dmitryshundrik.knowledgebase.model.art.dto.ArtistViewDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.service.art.ArtistService;
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
public class ArtistManagementController {

    private final ArtistService artistService;

    public ArtistManagementController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/management/artist/all")
    public String getAllArtists(Model model) {
        List<Artist> allSortedByCreatedDesc = artistService.getAllSortedByCreatedDesc();
        List<ArtistViewDTO> artistViewDTOList = artistService.getArtistViewDTOList(allSortedByCreatedDesc);
        model.addAttribute("artistList", artistViewDTOList);
        return "management/art/artist-archive";
    }

    @GetMapping("/management/artist/create")
    public String getArtistCreate(Model model) {
        ArtistCreateEditDTO artistCreateEditDTO = new ArtistCreateEditDTO();
        model.addAttribute("artistDTO", artistCreateEditDTO);
        model.addAttribute("genders", Gender.values());
        return "management/art/artist-create";
    }

    @PostMapping("/management/artist/create")
    public String postArtistCreate(@Valid @ModelAttribute("artistDTO") ArtistCreateEditDTO artistDTO,
                                    BindingResult bindingResult, Model model) {
        String error = artistService.artistSlugIsExist(artistDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute("slug", error);
            model.addAttribute("genders", Gender.values());
            return "management/art/artist-create";
        }
        String artistSlug = artistService.createArtist(artistDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistSlug;
    }

    @GetMapping("/management/artist/edit/{artistSlug}")
    public String getArtistEdit(@PathVariable String artistSlug, Model model) {
        Artist bySlug = artistService.getBySlug(artistSlug);
        ArtistCreateEditDTO artistCreateEditDTO = artistService.getArtistCreateEditDTO(bySlug);
        model.addAttribute("artistDTO", artistCreateEditDTO);
        model.addAttribute("genders", Gender.values());
        return "management/art/artist-edit";
    }

    @PutMapping("/management/artist/edit/{artistSlug}")
    public String putArtistEdit(@PathVariable String artistSlug,
                                 @ModelAttribute("artistDTO") ArtistCreateEditDTO artistDTO) {
        String artistDTOSlug = artistService.updateArtist(artistSlug, artistDTO).getSlug();
        return "redirect:/management/artist/edit/" + artistDTOSlug;
    }

    @PostMapping("/management/artist/edit/{artistSlug}/image/upload")
    public String postUploadArtistImage(@PathVariable String artistSlug,
                                        @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
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
        artistService.deleteArtistBySlug(artistSlug);
        return "redirect:/management/artist/all";
    }
}
