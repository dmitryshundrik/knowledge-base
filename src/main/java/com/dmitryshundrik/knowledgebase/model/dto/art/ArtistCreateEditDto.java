package com.dmitryshundrik.knowledgebase.model.dto.art;

import com.dmitryshundrik.knowledgebase.model.entity.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.util.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;
import static com.dmitryshundrik.knowledgebase.util.Constants.NICKNAME_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArtistCreateEditDto {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = NICKNAME_MUST_NOT_BE_BLANK)
    private String nickName;

    private String firstName;

    private String lastName;

    private Gender gender;

    private String image;

    private Integer born;

    private Integer died;

    @DateTimeFormat(pattern = DATE_FORMAT_YMD)
    private LocalDate birthDate;

    @DateTimeFormat(pattern = DATE_FORMAT_YMD)
    private LocalDate deathDate;

    private String approximateYears;

    private String birthplace;

    private String occupation;

    private List<PersonEvent> events;

    private List<PaintingViewDto> paintingList;

    private Boolean dateNotification;
}
