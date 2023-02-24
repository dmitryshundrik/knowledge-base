package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.music.SOTYList;
import com.dmitryshundrik.knowledgebase.model.music.dto.SOTYListCreateEditDTO;
import com.dmitryshundrik.knowledgebase.service.music.SOTYListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping()
public class SOTYListManagementController {

    @Autowired
    private SOTYListService sotyListService;

    @GetMapping("/management/soty-list/all")
    public String getAllSOTYLists(Model model) {
        List<SOTYList> allSOTYLists = sotyListService.getAllSOTYLists();
        model.addAttribute("sotyListViewDTOList", sotyListService.getSOTYListViewDTOList(allSOTYLists));
        return "management/soty-list-all";
    }

    @GetMapping("/management/soty-list/create")
    public String getCreateSOTYList(Model model) {
        model.addAttribute("sotyListCreateEditDTO", new SOTYListCreateEditDTO());
        return "management/soty-list-create";
    }

    @PostMapping("/management/soty-list/create")
    public String postCreateSOTYList(@Valid @ModelAttribute("sotyListCreateEditDTO") SOTYListCreateEditDTO sotyListCreateEditDTO,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "management/soty-list-create";
        }
        sotyListService.createSOTYListbySOTYListDTO(sotyListCreateEditDTO);
        return "redirect:/management/soty-list/all";
    }

    @GetMapping("/management/soty-list/edit/{slug}")
    public String getEditSOTYListBySlug(@PathVariable String slug, Model model) {
        SOTYList sotyListBySlug = sotyListService.getSOTYListBySlug(slug);
        model.addAttribute("sotyListCreateEditDTO", sotyListService.getSOTYListCreateEditDTO(sotyListBySlug));
        return "management/soty-list-edit";
    }

    @PutMapping("/management/soty-list/edit/{slug}")
    public String putEditSOTYListBySlug(@PathVariable String slug, @ModelAttribute("sotyListCreateEditDTO") SOTYListCreateEditDTO sotyListCreateEditDTO) {
        sotyListService.updateExistingSOTYList(sotyListCreateEditDTO, slug);
        return "redirect:/management/soty-list/edit/" + sotyListCreateEditDTO.getSlug();
    }

    @DeleteMapping("management/soty-list/delete/{slug}")
    public String deleteSOTYListById(@PathVariable String slug){
        sotyListService.deleteSOTYListBySlug(slug);
        return "redirect:/management/soty-list/all";
    }
}
