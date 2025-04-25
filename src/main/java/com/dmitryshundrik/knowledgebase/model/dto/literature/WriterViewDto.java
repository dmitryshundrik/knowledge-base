package com.dmitryshundrik.knowledgebase.model.dto.literature;

import com.dmitryshundrik.knowledgebase.model.dto.core.PersonEventDto;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WriterViewDto {

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

    private String birthplace;

    private String occupation;

    private List<PersonEventDto> events;

    protected List<ProseViewDto> proseList;

    private List<QuoteViewDto> quoteList;

    private List<WordDto> wordList;

    private Boolean dateNotification;
}
