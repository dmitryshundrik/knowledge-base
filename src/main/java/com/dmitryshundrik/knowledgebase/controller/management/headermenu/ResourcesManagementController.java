package com.dmitryshundrik.knowledgebase.controller.management.headermenu;

import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.dto.core.ResourceDto;
import com.dmitryshundrik.knowledgebase.service.core.ResourcesService;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE;
import static com.dmitryshundrik.knowledgebase.util.Constants.RESOURCE_LIST;

@Controller
@RequiredArgsConstructor
public class ResourcesManagementController {

    private final ResourcesService resourcesService;

    @GetMapping("/management/resource/all")
    public String getAllResources(Model model) {
        List<Resource> resourceList = resourcesService.getAllOrderByCreated();
        List<ResourceDto> resourceDtoList = resourcesService.getResourceDtoList(resourceList);
        model.addAttribute(RESOURCE_LIST, resourceDtoList);
        return "management/headermenu/resource-all";
    }

    @GetMapping("/management/resource/create")
    public String getResourceCreate(Model model) {
        ResourceDto resourceDto = new ResourceDto();
        model.addAttribute(RESOURCE, resourceDto);
        model.addAttribute("resourceTypeList", ResourceType.values());
        return "management/headermenu/resource-create";
    }

    @PostMapping("/management/resource/create")
    public String postResourceCreate(@ModelAttribute(RESOURCE) ResourceDto resourceDto) {
        String resourceDtoId = resourcesService.createResource(resourceDto).getId();
        return "redirect:/management/resource/edit/" + resourceDtoId;
    }

    @GetMapping("/management/resource/edit/{resourceId}")
    public String getResourceEdit(@PathVariable String resourceId, Model model) {
        Resource resource = resourcesService.getById(resourceId);
        ResourceDto resourceDto = resourcesService.getResourceDto(resource);
        model.addAttribute(RESOURCE, resourceDto);
        model.addAttribute("resourceTypeList", ResourceType.values());
        return "management/headermenu/resource-edit";
    }

    @PutMapping("/management/resource/edit/{resourceId}")
    public String putResourceEdit(@PathVariable String resourceId,
                                  @ModelAttribute(RESOURCE) ResourceDto resourceDto) {
        String resourceDtoId = resourcesService.updateResource(resourceId, resourceDto).getId();
        return "redirect:/management/resource/edit/" + resourceDtoId;
    }

    @DeleteMapping("/management/resource/delete/{resourceId}")
    public String deleteResourceById(@PathVariable String resourceId) {
        resourcesService.deleteResource(resourceId);
        return "redirect:/management/resource/all";
    }
}
