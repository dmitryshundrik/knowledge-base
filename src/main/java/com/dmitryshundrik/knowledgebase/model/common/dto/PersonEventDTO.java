package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
public class PersonEventDTO {

    private String id;

    private String created;

    private Integer year;

    private Integer anotherYear;

    private String title;

    @NotBlank(message = "description may not be blank")
    private String description;

    public PersonEventDTO () {

    }

}
