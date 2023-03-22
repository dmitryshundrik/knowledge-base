package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

}
