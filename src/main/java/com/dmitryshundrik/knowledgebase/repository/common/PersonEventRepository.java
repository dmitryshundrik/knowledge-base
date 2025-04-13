package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.entity.common.PersonEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface PersonEventRepository extends JpaRepository<PersonEvent, UUID> {

}
