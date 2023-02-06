package com.dmitryshundrik.knowledgebase.repository;

import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {

}
