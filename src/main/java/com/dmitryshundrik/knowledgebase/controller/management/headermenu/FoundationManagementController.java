package com.dmitryshundrik.knowledgebase.controller.management.headermenu;

import com.dmitryshundrik.knowledgebase.model.common.Foundation;
import com.dmitryshundrik.knowledgebase.model.common.dto.FoundationDTO;
import com.dmitryshundrik.knowledgebase.service.common.FoundationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class FoundationManagementController {

    private final FoundationService foundationService;

    public FoundationManagementController(FoundationService foundationService) {
        this.foundationService = foundationService;
    }

    @GetMapping("/management/foundation/all")
    public String getAllFoundations(Model model) {
        List<Foundation> foundationList = foundationService.getAllSortedByCreated();
        List<FoundationDTO> foundationDTOList = foundationService.getFoundationDTOList(foundationList);
        model.addAttribute("foundations", foundationDTOList);
        return "management/headermenu/foundation-all";
    }

    @GetMapping("/management/foundation/create")
    public String getFoundationCreate(Model model) {
        FoundationDTO foundationDTO = new FoundationDTO();
        model.addAttribute("foundationDTO", foundationDTO);
        return "management/headermenu/foundation-create";
    }

    @PostMapping("/management/foundation/create")
    public String postFoundationCreate(@ModelAttribute("foundationDTO") FoundationDTO foundationDTO) {
        Foundation foundation = foundationService.createFoundation(foundationDTO);
        return "redirect:/management/foundation/edit/" + foundation.getId();
    }

    @GetMapping("/management/foundation/edit/{foundationId}")
    public String getFoundationEdit(@PathVariable String foundationId, Model model) {
        Foundation byId = foundationService.getById(foundationId);
        FoundationDTO foundationDTO = foundationService.getFoundationDTO(byId);
        model.addAttribute("foundationDTO", foundationDTO);
        return "management/headermenu/foundation-edit";
    }

    @PutMapping("/management/foundation/edit/{foundationId}")
    public String putFoundationEdit(@PathVariable String foundationId,
                                    @ModelAttribute("foundationDTO") FoundationDTO foundationDTO) {
        Foundation foundation = foundationService.updateFoundation(foundationId, foundationDTO);
        return "redirect:/management/foundation/edit/" + foundation.getId();
    }

    @DeleteMapping("/management/foundation/delete/{foundationId}")
    public String deleteFoundationById(@PathVariable String foundationId) {
        foundationService.deleteFoundation(foundationId);
        return "redirect:/management/foundation/all";
    }
}
