package com.dmitryshundrik.knowledgebase.dto.art;

import com.dmitryshundrik.knowledgebase.entity.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.util.enums.Gender;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;

@Data
@Builder
public class ArtistViewDto {

    private String created;

    private String slug;

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
