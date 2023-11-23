package com.dmitryshundrik.knowledgebase.model.literature.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.PersonEventDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class WriterCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "nickname may not be blank")
    private String nickName;

    private String firstName;

    private String lastName;

    private String image;

    private Integer born;

    private Integer died;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate deathDate;

    private String birthplace;

    private String occupation;

    private List<PersonEventDTO> events;

    protected List<ProseViewDTO> proseList;

    private List<QuoteViewDTO> quoteList;

    private List<WordDTO> wordList;

    public WriterCreateEditDTO() {

    }

}
