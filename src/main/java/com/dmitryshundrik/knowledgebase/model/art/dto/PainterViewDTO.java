package com.dmitryshundrik.knowledgebase.model.art.dto;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class PainterViewDTO {

    private String created;

    private String slug;

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

    private String approximateYears;

    private String birthplace;

    private String occupation;

    private List<PersonEvent> events;

    private List<PaintingViewDTO> paintingList;

}
