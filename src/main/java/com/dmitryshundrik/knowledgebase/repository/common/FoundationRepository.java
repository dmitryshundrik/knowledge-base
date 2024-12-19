package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.entity.common.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FoundationRepository extends JpaRepository<Foundation, UUID> {

    List<Foundation> getAllByOrderByCreatedAsc();
}
