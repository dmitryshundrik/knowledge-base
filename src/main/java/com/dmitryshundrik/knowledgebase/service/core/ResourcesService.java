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

    public List<Resource> getAllByResourceType(ResourceType resourceType) {
        return resourcesRepository.findAllByResourceTypeOrderByCreatedAsc(resourceType);
    }

    public List<Resource> getAllOrderByCreated() {
        return resourcesRepository.findAllByOrderByCreatedAsc();
    }

    public List<Resource> getLatestUpdate() {
        return resourcesRepository.findFirst20ByOrderByCreatedDesc();
    }

    public ResourceDto createResource(ResourceDto resourceDto) {
        Resource resource = new Resource();
        setFieldsFromDto(resource, resourceDto);
        return getResourceDto(resourcesRepository.save(resource));
    }

    public ResourceDto updateResource(String resourceId, ResourceDto resourceDTO) {
        Resource byId = getById(resourceId);
        setFieldsFromDto(byId, resourceDTO);
        return getResourceDto(byId);
    }

    public void deleteResource(String resourceId) {
        resourcesRepository.deleteById(UUID.fromString(resourceId));
    }

    public ResourceDto getResourceDto(Resource resource) {
        return ResourceDto.builder()
                .id(resource.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(resource.getCreated()))
                .title(resource.getTitle())
                .description(resource.getDescription())
                .link(resource.getLink())
                .resourceType(resource.getResourceType())
                .build();
    }

    public List<ResourceDto> getResourceDtoList(List<Resource> resourceList) {
        return resourceList.stream()
                .map(this::getResourceDto)
                .collect(Collectors.toList());
    }

    public void setFieldsFromDto(Resource resource, ResourceDto resourceDTO) {
        resource.setTitle(resourceDTO.getTitle());
        resource.setDescription(resourceDTO.getDescription());
        resource.setLink(resourceDTO.getLink());
        resource.setResourceType(resourceDTO.getResourceType());
    }
}
