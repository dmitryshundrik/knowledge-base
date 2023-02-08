package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.AcademicGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.ContemporaryGenre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.timeline.Event;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class MusicianViewDTO {

    private String created;

    private String slug;

    private String firstName;

    private String lastName;

    private String nickName;

    private String image;

    private Integer born;

    private Integer died;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private Period period;

    private List<AcademicGenre> academicGenres;

    private List<ContemporaryGenre> contemporaryGenres;

    private String spotifyLink;

    private List<EventDTO> events;
}
