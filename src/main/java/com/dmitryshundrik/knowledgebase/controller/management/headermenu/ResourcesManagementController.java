package com.dmitryshundrik.knowledgebase.controller.management.headermenu;

import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.common.dto.ResourceDTO;
import com.dmitryshundrik.knowledgebase.service.common.ResourcesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping
public class ResourcesManagementController {

    @Autowired
    private ResourcesService resourcesService;

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
        model.addAttribute("resourceDTO", resourceDTO);
        return "management/headermenu/resource-create";
    }

    @PostMapping("/management/resource/create")
    public String postResourceCreate(@ModelAttribute("resourceDTO") ResourceDTO resourceDTO) {
        Resource resource = resourcesService.createResource(resourceDTO);
        return "redirect:/management/resource/edit/" + resource.getId();
    }

    @GetMapping("/management/resource/edit/{resourceId}")
    public String getResourceEdit(@PathVariable String resourceId, Model model) {
        Resource byId = resourcesService.getById(resourceId);
        ResourceDTO resourceDTO = resourcesService.getResourceDTO(byId);
        model.addAttribute("resourceDTO", resourceDTO);
        return "management/headermenu/resource-edit";
    }

    @PutMapping("/management/resource/edit/{resourceId}")
    public String putResourceEdit(@PathVariable String resourceId,
                                    @ModelAttribute("resourceDTO") ResourceDTO resourceDTO) {
        Resource resource = resourcesService.updateResource(resourceId, resourceDTO);
        return "redirect:/management/resource/edit/" + resource.getId();
    }

    @DeleteMapping("/management/resource/delete/{resourceId}")
    public String deleteResourceById(@PathVariable String resourceId) {
        resourcesService.deleteResource(resourceId);
        return "redirect:/management/resource/all";
    }

}
