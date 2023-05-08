package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.common.Foundation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FoundationRepository extends JpaRepository<Foundation, UUID> {
}
