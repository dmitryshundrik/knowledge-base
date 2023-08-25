package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.common.Resource;
import com.dmitryshundrik.knowledgebase.model.common.dto.ResourceDTO;
import com.dmitryshundrik.knowledgebase.repository.common.ResourcesRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class ResourcesService {

    @Autowired
    private ResourcesRepository resourcesRepository;

    public Resource getById(String id) {
        return resourcesRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Resource> getAll() {
        return resourcesRepository.findAll();
    }

    public List<Resource> getAllSortedByCreated() {
        return getAll().stream()
                .sorted(Comparator.comparing(Resource::getCreated))
                .collect(Collectors.toList());
    }

    public ResourceDTO createResource(ResourceDTO resourceDTO) {
        Resource resource = new Resource();
        resource.setCreated(Instant.now());
        setFieldsFromDTO(resource, resourceDTO);
        return getResourceDTO(resourcesRepository.save(resource));
    }

    public ResourceDTO updateResource(String resourceId, ResourceDTO resourceDTO) {
        Resource byId = getById(resourceId);
        setFieldsFromDTO(byId, resourceDTO);
        return getResourceDTO(byId);
    }

    public void deleteResource(String resourceId) {
        resourcesRepository.deleteById(UUID.fromString(resourceId));
    }

    public ResourceDTO getResourceDTO(Resource resource) {
        return ResourceDTO.builder()
                .id(resource.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(resource.getCreated()))
                .title(resource.getTitle())
                .description(resource.getDescription())
                .link(resource.getLink())
                .build();
    }

    public List<ResourceDTO> getResourceDTOList(List<Resource> resourceList) {
        return resourceList.stream()
                .map(this::getResourceDTO)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDTO(Resource resource, ResourceDTO resourceDTO) {
        resource.setTitle(resourceDTO.getTitle());
        resource.setDescription(resourceDTO.getDescription());
        resource.setLink(resourceDTO.getLink());
    }

    public List<Resource> getLatestUpdate() {
        return resourcesRepository.findFirst20ByOrderByCreatedDesc();
    }

}
