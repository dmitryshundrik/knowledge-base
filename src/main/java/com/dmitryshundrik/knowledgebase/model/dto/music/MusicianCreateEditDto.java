package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.dto.core.PersonEventDto;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;
import static com.dmitryshundrik.knowledgebase.util.Constants.NICKNAME_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicianCreateEditDto {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = NICKNAME_MUST_NOT_BE_BLANK)
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

    private SortType albumsSortType;

    private SortType compositionsSortType;

    private String spotifyLink;

    private List<PersonEventDto> events;

    private List<AlbumViewDto> albums;

    private List<CompositionViewDto> compositions;

    private Boolean dateNotification;
}
