package com.dmitryshundrik.knowledgebase.repository.common;

import com.dmitryshundrik.knowledgebase.model.common.Event;
import com.dmitryshundrik.knowledgebase.model.common.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findAllByEventType(EventType type);

}
