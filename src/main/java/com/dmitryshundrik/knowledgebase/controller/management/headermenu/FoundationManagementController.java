package com.dmitryshundrik.knowledgebase.controller.management.headermenu;

import com.dmitryshundrik.knowledgebase.model.entity.core.Foundation;
import com.dmitryshundrik.knowledgebase.model.dto.core.FoundationDto;
import com.dmitryshundrik.knowledgebase.service.core.FoundationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.FOUNDATION;
import static com.dmitryshundrik.knowledgebase.util.Constants.FOUNDATION_LIST;

@Controller
@RequiredArgsConstructor
public class FoundationManagementController {

    private final FoundationService foundationService;

    @GetMapping("/management/foundation/all")
    public String getAllFoundations(Model model) {
        List<Foundation> foundationList = foundationService.getAllSortedByCreated();
        List<FoundationDto> foundationDtoList = foundationService.getFoundationDTOList(foundationList);
        model.addAttribute(FOUNDATION_LIST, foundationDtoList);
        return "management/headermenu/foundation-all";
    }

    @GetMapping("/management/foundation/create")
    public String getFoundationCreate(Model model) {
        FoundationDto foundationDTO = new FoundationDto();
        model.addAttribute(FOUNDATION, foundationDTO);
        return "management/headermenu/foundation-create";
    }

    @PostMapping("/management/foundation/create")
    public String postFoundationCreate(@ModelAttribute(FOUNDATION) FoundationDto foundationDto) {
        Foundation foundation = foundationService.createFoundation(foundationDto);
        return "redirect:/management/foundation/edit/" + foundation.getId();
    }

    @GetMapping("/management/foundation/edit/{foundationId}")
    public String getFoundationEdit(@PathVariable String foundationId, Model model) {
        Foundation byId = foundationService.getById(foundationId);
        FoundationDto foundationDto = foundationService.getFoundationDto(byId);
        model.addAttribute(FOUNDATION, foundationDto);
        return "management/headermenu/foundation-edit";
    }

    @PutMapping("/management/foundation/edit/{foundationId}")
    public String putFoundationEdit(@PathVariable String foundationId,
                                    @ModelAttribute(FOUNDATION) FoundationDto foundationDto) {
        Foundation foundation = foundationService.updateFoundation(foundationId, foundationDto);
        return "redirect:/management/foundation/edit/" + foundation.getId();
    }

    @DeleteMapping("/management/foundation/delete/{foundationId}")
    public String deleteFoundationById(@PathVariable String foundationId) {
        foundationService.deleteFoundation(foundationId);
        return "redirect:/management/foundation/all";
    }
}
