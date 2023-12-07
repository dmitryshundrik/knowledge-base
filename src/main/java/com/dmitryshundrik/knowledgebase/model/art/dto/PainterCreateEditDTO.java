package com.dmitryshundrik.knowledgebase.model.art.dto;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class PainterCreateEditDTO {

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

    private String approximateYears;

    private String birthplace;

    private String occupation;

    private List<PersonEvent> events;

    private List<PaintingViewDTO> paintingList;

    public PainterCreateEditDTO() {

    }
}
