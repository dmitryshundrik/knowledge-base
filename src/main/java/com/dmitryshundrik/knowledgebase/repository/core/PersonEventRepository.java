package com.dmitryshundrik.knowledgebase.repository.core;

import com.dmitryshundrik.knowledgebase.model.entity.core.PersonEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PersonEventRepository extends JpaRepository<PersonEvent, UUID> {

}
