package com.dmitryshundrik.knowledgebase.repository.art;

import com.dmitryshundrik.knowledgebase.entity.art.PaintingStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PaintingStyleRepository extends JpaRepository<PaintingStyle, UUID> {

}
