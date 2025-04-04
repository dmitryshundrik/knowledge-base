package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.entity.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.dto.common.PersonEventDTO;
import com.dmitryshundrik.knowledgebase.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.repository.common.PersonEventRepository;
import com.dmitryshundrik.knowledgebase.util.InstantFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PersonEventService {

    private final PersonEventRepository personEventRepository;

    public PersonEvent getPersonEventById(String id) {
        return personEventRepository.findById(UUID.fromString(id)).orElse(null);
    }

    public PersonEventDTO createPersonEvent(PersonEventDTO personEventDTO) {
        PersonEvent personEvent = new PersonEvent();
        setFieldsFromDTO(personEvent, personEventDTO);
        return getPersonEventDTO(personEventRepository.save(personEvent));
    }

    public PersonEventDTO updatePersonEvent(String eventId, PersonEventDTO personEventDTO) {
        PersonEvent personEventById = getPersonEventById(eventId);
        setFieldsFromDTO(personEventById, personEventDTO);
        return getPersonEventDTO(personEventById);
    }

    public String createMusicianEvent(Musician musician, PersonEventDTO personEventDTO) {
        String personEventId = createPersonEvent(personEventDTO).getId();
        musician.getEvents().add(getPersonEventById(personEventId));
        return personEventId;
    }

    public void deleteMusicianEventById(Musician musician, String id) {
        musician.getEvents().removeIf(event -> event.getId().toString().equals(id));
        personEventRepository.deleteById(UUID.fromString(id));
    }

    public PersonEventDTO getPersonEventDTO(PersonEvent personEvent) {
        return PersonEventDTO.builder()
                .id(personEvent.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(personEvent.getCreated()))
                .year(personEvent.getYear())
                .anotherYear(personEvent.getAnotherYear())
                .title(personEvent.getTitle())
                .description(personEvent.getDescription())
                .build();
    }

    public List<PersonEventDTO> getPersonEventDTOList(List<PersonEvent> personEventList) {
        return personEventList.stream().map(this::getPersonEventDTO).collect(Collectors.toList());
    }

    private void setFieldsFromDTO(PersonEvent personEvent, PersonEventDTO personEventDTO) {
        personEvent.setYear(personEventDTO.getYear());
        personEvent.setAnotherYear(personEventDTO.getAnotherYear());
        personEvent.setTitle(personEventDTO.getTitle());
        personEvent.setDescription(personEventDTO.getDescription());
    }
}
