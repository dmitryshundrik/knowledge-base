package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.PersonEventDTO;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    private Integer founded;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private String birthplace;

    private String occupation;

    private List<MusicPeriod> musicPeriods;

    private List<MusicGenre> musicGenres;

    private String spotifyLink;

    private List<PersonEventDTO> events;

    private List<AlbumViewDTO> albums;

    private List<AlbumViewDTO> essentialAlbums;

    private List<CompositionViewDTO> compositions;

    private List<CompositionViewDTO> essentialCompositions;

}
