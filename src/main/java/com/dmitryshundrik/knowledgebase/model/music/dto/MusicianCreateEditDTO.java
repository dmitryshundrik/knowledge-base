package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.PersonEventDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.music.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicianCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "nickname may not be blank")
    private String nickName;

    private String nickNameEn;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String image;

    private Integer born;

    private Integer died;

    private Integer founded;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private String birthplace;

    private String based;

    private String occupation;

    private String catalogTitle;

    private List<MusicPeriod> musicPeriods;

    private SortType albumsSortType;

    private SortType compositionsSortType;

    private String spotifyLink;

    private List<PersonEventDTO> events;

    private List<AlbumViewDTO> albums;

    private List<CompositionViewDTO> compositions;

}
