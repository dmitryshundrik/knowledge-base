package com.dmitryshundrik.knowledgebase.controller.management.cinema;

import com.dmitryshundrik.knowledgebase.dto.cinema.FilmArchiveDto;
import com.dmitryshundrik.knowledgebase.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.entity.cinema.Film;
import com.dmitryshundrik.knowledgebase.service.cinema.FilmService;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.FILM;
import static com.dmitryshundrik.knowledgebase.util.Constants.FILM_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class FilmManagementController {

    private final FilmService filmService;

    @GetMapping("/management/film/all")
    public String getFilmArchive(Model model) {
        List<FilmArchiveDto> filmList = filmService.getAllFilmArchiveDto();
        model.addAttribute(FILM_LIST, filmList);
        return "management/cinema/film-archive";
    }

    @GetMapping("/management/film/create")
    public String getFilmCreate(Model model) {
        FilmCreateEditDto filmCreateEditDto = new FilmCreateEditDto();
        model.addAttribute(FILM, filmCreateEditDto);
        return "management/cinema/film-create";
    }

    @PostMapping("/management/film/create")
    public String postFilmCreate(@Valid @ModelAttribute(FILM) FilmCreateEditDto filmDto,
                                 BindingResult bindingResult, Model model) {
        String error = filmService.filmSlugIsExist(filmDto.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/cinema/film-create";
        }
        String filmSlug = filmService.createFilm(filmDto).getSlug();
        return "redirect:/management/film/edit/" + filmSlug;
    }

    @GetMapping("/management/film/edit/{filmSlug}")
    public String getFilmEdit(@PathVariable String filmSlug, Model model) {
        Film bySlug = filmService.getBySlug(filmSlug);
        FilmCreateEditDto filmCreateEditDto = filmService.getFilmCreateEditDto(bySlug);
        model.addAttribute(FILM, filmCreateEditDto);
        return "management/cinema/film-edit";
    }

    @PutMapping("/management/film/edit/{filmSlug}")
    public String putFilmEdit(@PathVariable String filmSlug,
                                @ModelAttribute(FILM) FilmCreateEditDto filmDTO) {
        String filmDTOSlug = filmService.updateFilm(filmSlug, filmDTO).getSlug();
        return "redirect:/management/film/edit/" + filmDTOSlug;
    }

    @PostMapping("/management/film/edit/{filmSlug}/image/upload")
    public String postUploadFilmImage(@PathVariable String filmSlug,
                                      @RequestParam("file") MultipartFile file) throws IOException {
        byte[] bytes = Base64.encodeBase64(file.getBytes());
        filmService.updateFilmImageBySlug(filmSlug, bytes);
        return "redirect:/management/film/edit/" + filmSlug;
    }

    @DeleteMapping("/management/film/edit/{filmSlug}/image/delete")
    public String deleteFilmImage(@PathVariable String filmSlug) {
        filmService.deleteFilmImage(filmSlug);
        return "redirect:/management/film/edit/" + filmSlug;
    }

    @DeleteMapping("/management/film/delete/{filmSlug}")
    public String deleteFilmBySlug(@PathVariable String filmSlug) {
        filmService.deleteFilmBySlug(filmSlug);
        return "redirect:/management/film/all";
    }
}
