package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.Genre;
import com.dmitryshundrik.knowledgebase.model.music.enums.Period;
import com.dmitryshundrik.knowledgebase.model.music.enums.Style;
import com.dmitryshundrik.knowledgebase.model.timeline.EventDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class MusicianCreateEditDTO {

    private String slug;

    private String firstName;

    private String lastName;

    private String nickName;

    private String image;

    private Integer born;

    private Integer died;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private Period period;

    private List<Style> styles;

    private List<Genre> genres;

    private List<EventDTO> events;

    public MusicianCreateEditDTO () {

    }

}
