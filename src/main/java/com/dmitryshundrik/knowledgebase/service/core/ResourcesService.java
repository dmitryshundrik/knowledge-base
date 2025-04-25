package com.dmitryshundrik.knowledgebase.service.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.dto.core.ResourceDto;
import com.dmitryshundrik.knowledgebase.repository.core.ResourcesRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResourcesService {

    private final ResourcesRepository resourcesRepository;

    public Resource getById(String id) {
        return resourcesRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public List<Resource> getAll() {
        return resourcesRepository.findAll();
    }

    public List<Resource> getAllByResourceType(ResourceType resourceType) {
        return resourcesRepository.findAllByResourceTypeOrderByCreatedAsc(resourceType);
    }

    public List<Resource> getAllSortedByCreated() {
        return resourcesRepository.findAllByOrderByCreatedAsc();
    }

    public ResourceDto createResource(ResourceDto resourceDTO) {
        Resource resource = new Resource();
        setFieldsFromDTO(resource, resourceDTO);
        return getResourceDTO(resourcesRepository.save(resource));
    }

    public ResourceDto updateResource(String resourceId, ResourceDto resourceDTO) {
        Resource byId = getById(resourceId);
        setFieldsFromDTO(byId, resourceDTO);
        return getResourceDTO(byId);
    }

    public void deleteResource(String resourceId) {
        resourcesRepository.deleteById(UUID.fromString(resourceId));
    }

    public ResourceDto getResourceDTO(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(resource.getCreated()))
                .title(resource.getTitle())
                .description(resource.getDescription())
                .link(resource.getLink())
                .resourceType(resource.getResourceType())
                .build();
    }

    public List<ResourceDto> getResourceDTOList(List<Resource> resourceList) {
        return resourceList.stream()
                .map(this::getResourceDTO)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDTO(Resource resource, ResourceDto resourceDTO) {
        resource.setTitle(resourceDTO.getTitle());
        resource.setDescription(resourceDTO.getDescription());
        resource.setLink(resourceDTO.getLink());
        resource.setResourceType(resourceDTO.getResourceType());
    }

    public List<Resource> getLatestUpdate() {
        return resourcesRepository.findFirst20ByOrderByCreatedDesc();
    }
}
