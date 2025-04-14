package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Resource;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ResourcesRepository extends JpaRepository<Resource, UUID> {

    List<Resource> findFirst20ByOrderByCreatedDesc();

    List<Resource> findAllByOrderByCreatedAsc();

    List<Resource> findAllByResourceTypeOrderByCreatedAsc(ResourceType resourceType);
}
