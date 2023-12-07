package com.dmitryshundrik.knowledgebase.model.literature.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProseCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private String writerNickname;

    private String writerSlug;

    private Integer year;

    private Double rating;

    private String description;

    private List<QuoteViewDTO> quoteList;

    public ProseCreateEditDTO() {

    }

}
