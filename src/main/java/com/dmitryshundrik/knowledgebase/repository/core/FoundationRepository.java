package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface FoundationRepository extends JpaRepository<Foundation, UUID> {

    List<Foundation> findAllByOrderByCreatedAsc();
}
