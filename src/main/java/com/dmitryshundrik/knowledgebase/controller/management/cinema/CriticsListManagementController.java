package com.dmitryshundrik.knowledgebase.controller.management.cinema;

import com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListCreateEditDto;
import com.dmitryshundrik.knowledgebase.dto.cinema.CriticsListResponseDto;
import com.dmitryshundrik.knowledgebase.dto.cinema.FilmCreateEditDto;
import com.dmitryshundrik.knowledgebase.entity.cinema.CriticsList;
import com.dmitryshundrik.knowledgebase.service.cinema.CriticsListService;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.CRITICS_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.CRITICS_LIST_LIST;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG;

@Controller
@RequiredArgsConstructor
public class CriticsListManagementController {

    private final CriticsListService criticsListService;

    @GetMapping("/management/critics-list/all")
    public String getCriticsListArchive(Model model) {
        List<CriticsListResponseDto> criticsList = criticsListService.getAllCriticsListArchiveDto();
        model.addAttribute(CRITICS_LIST_LIST, criticsList);
        return "management/cinema/critics-list-archive";
    }

    @GetMapping("/management/critics-list/create")
    public String getCriticsListCreate(Model model) {
        FilmCreateEditDto filmCreateEditDto = new FilmCreateEditDto();
        model.addAttribute(CRITICS_LIST, filmCreateEditDto);
        return "management/cinema/critics-list-create";
    }

    @PostMapping("/management/critics-list/create")
    public String postCriticsListCreate(@Valid @ModelAttribute(CRITICS_LIST) CriticsListCreateEditDto criticsListDTO, BindingResult bindingResult, Model model) {
        String error = criticsListService.criticsListSlugIsExist(criticsListDTO.getSlug());
        if (!error.isEmpty() || bindingResult.hasErrors()) {
            model.addAttribute(SLUG, error);
            return "management/cinema/critics-list-create";
        }
        String criticsListSlug = criticsListService.createCriticsList(criticsListDTO).getSlug();
        return "redirect:/management/critics-list/edit/" + criticsListSlug;
    }

    @GetMapping("/management/critics-list/edit/{criticsListSlug}")
    public String getCriticsListEdit(@PathVariable String criticsListSlug, Model model) {
        CriticsList bySlug = criticsListService.getBySlug(criticsListSlug);
        CriticsListCreateEditDto criticsListCreateEditDto = criticsListService.getCriticsListCreateEditDto(bySlug);
        model.addAttribute(CRITICS_LIST, criticsListCreateEditDto);
        return "management/cinema/critics-list-edit";
    }

    @PutMapping("/management/critics-list/edit/{criticsListSlug}")
    public String putCriticsListEdit(@PathVariable String criticsListSlug, @ModelAttribute(CRITICS_LIST) CriticsListCreateEditDto criticsListDTO) {
        String criticsListDTOSlug = criticsListService.updateCriticsList(criticsListSlug, criticsListDTO).getSlug();
        return "redirect:/management/critics-list/edit/" + criticsListDTOSlug;
    }

    @DeleteMapping("/management/critics-list/delete/{criticsListSlug}")
    public String deleteFilmBySlug(@PathVariable String criticsListSlug) {
        criticsListService.deleteCriticsListBySlug(criticsListSlug);
        return "redirect:/management/critics-list/all";
    }
}
