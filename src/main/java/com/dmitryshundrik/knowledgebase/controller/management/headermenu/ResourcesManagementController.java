package com.dmitryshundrik.knowledgebase.controller.management.headermenu;

import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.common.dto.ResourceDTO;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.List;

@Controller
public class ResourcesManagementController {

    private final ResourcesService resourcesService;

    public ResourcesManagementController(ResourcesService resourcesService) {
        this.resourcesService = resourcesService;
    }

    @GetMapping("/management/resource/all")
    public String getAllResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllSortedByCreated();
        List<ResourceDTO> resourceDTOList = resourcesService.getResourceDTOList(resourceList);
        model.addAttribute("resources", resourceDTOList);
        return "management/headermenu/resource-all";
    }

    @GetMapping("/management/resource/create")
    public String getResourceCreate(Model model) {
        ResourceDTO resourceDTO = new ResourceDTO();
        model.addAttribute("resource", resourceDTO);
        return "management/headermenu/resource-create";
    }

    @PostMapping("/management/resource/create")
    public String postResourceCreate(@ModelAttribute("resource") ResourceDTO resourceDTO) {
        String resourceDTOId = resourcesService.createResource(resourceDTO).getId();
        return "redirect:/management/resource/edit/" + resourceDTOId;
    }

    @GetMapping("/management/resource/edit/{resourceId}")
    public String getResourceEdit(@PathVariable String resourceId, Model model) {
        Resource byId = resourcesService.getById(resourceId);
        ResourceDTO resourceDTO = resourcesService.getResourceDTO(byId);
        model.addAttribute("resource", resourceDTO);
        return "management/headermenu/resource-edit";
    }

    @PutMapping("/management/resource/edit/{resourceId}")
    public String putResourceEdit(@PathVariable String resourceId,
                                  @ModelAttribute("resource") ResourceDTO resourceDTO) {
        String resourceDTOId = resourcesService.updateResource(resourceId, resourceDTO).getId();
        return "redirect:/management/resource/edit/" + resourceDTOId;
    }

    @DeleteMapping("/management/resource/delete/{resourceId}")
    public String deleteResourceById(@PathVariable String resourceId) {
        resourcesService.deleteResource(resourceId);
        return "redirect:/management/resource/all";
    }
}
