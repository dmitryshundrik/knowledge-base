package com.dmitryshundrik.knowledgebase.service.common;

import com.dmitryshundrik.knowledgebase.model.entity.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.dto.common.PersonEventDto;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
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

    public PersonEventDto createPersonEvent(PersonEventDto personEventDto) {
        PersonEvent personEvent = new PersonEvent();
        setFieldsFromDto(personEvent, personEventDto);
        return getPersonEventDto(personEventRepository.save(personEvent));
    }

    public PersonEventDto updatePersonEvent(String eventId, PersonEventDto personEventDto) {
        PersonEvent personEventById = getPersonEventById(eventId);
        setFieldsFromDto(personEventById, personEventDto);
        return getPersonEventDto(personEventById);
    }

    public String createMusicianEvent(Musician musician, PersonEventDto personEventDto) {
        String personEventId = createPersonEvent(personEventDto).getId();
        musician.getEvents().add(getPersonEventById(personEventId));
        return personEventId;
    }

    public void deleteMusicianEventById(Musician musician, String id) {
        musician.getEvents().removeIf(event -> event.getId().toString().equals(id));
        personEventRepository.deleteById(UUID.fromString(id));
    }

    public PersonEventDto getPersonEventDto(PersonEvent personEvent) {
        return PersonEventDto.builder()
                .id(personEvent.getId().toString())
                .created(InstantFormatter.instantFormatterYMD(personEvent.getCreated()))
                .year(personEvent.getYear())
                .anotherYear(personEvent.getAnotherYear())
                .title(personEvent.getTitle())
                .description(personEvent.getDescription())
                .build();
    }

    public List<PersonEventDto> getPersonEventDtoList(List<PersonEvent> personEventList) {
        return personEventList.stream().map(this::getPersonEventDto).collect(Collectors.toList());
    }

    private void setFieldsFromDto(PersonEvent personEvent, PersonEventDto personEventDto) {
        personEvent.setYear(personEventDto.getYear());
        personEvent.setAnotherYear(personEventDto.getAnotherYear());
        personEvent.setTitle(personEventDto.getTitle());
        personEvent.setDescription(personEventDto.getDescription());
    }
}
