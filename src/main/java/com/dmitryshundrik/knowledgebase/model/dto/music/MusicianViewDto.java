package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.dto.core.PersonEventDto;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;

@Data
@Builder
public class MusicianViewDto {

    private String created;

    private String slug;

    private String nickName;

    private String nickNameEn;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String image;

    private Integer born;

    private Integer died;

    private Integer founded;

    @DateTimeFormat(pattern = DATE_FORMAT_YMD)
    private LocalDate birthDate;

    @DateTimeFormat(pattern = DATE_FORMAT_YMD)
    private LocalDate deathDate;

    private String birthplace;

    private String based;

    private String occupation;

    private String catalogTitle;

    private List<MusicPeriod> musicPeriods;

    private List<MusicGenre> musicGenres;

    private String spotifyLink;

    private List<PersonEventDto> events;

    private List<AlbumViewDto> albums;

    private List<AlbumViewDto> collaborations;

    private List<AlbumViewDto> essentialAlbums;

    private List<CompositionViewDto> compositions;

    private List<CompositionViewDto> essentialCompositions;
}
