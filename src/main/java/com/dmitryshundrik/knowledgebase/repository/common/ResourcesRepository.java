package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.entity.common.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ResourcesRepository extends JpaRepository<Resource, UUID> {

    List<Resource> findFirst20ByOrderByCreatedDesc();
}
