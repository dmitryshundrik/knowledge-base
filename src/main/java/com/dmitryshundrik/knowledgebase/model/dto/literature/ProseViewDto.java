package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class ProseViewDto {

    private String created;

    private String slug;

    private String title;

    private String writerNickname;

    private String writerSlug;

    private Integer year;

    private Double rating;

    private String playCharactersSchema;

    private String synopsis;

    private String description;

    private List<QuoteViewDto> quoteList;
}
