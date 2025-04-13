package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.TITLE_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProseCreateEditDTO {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    private String title;

    private String writerNickname;

    private String writerSlug;

    private Integer year;

    private Double rating;

    private String playCharactersSchema;

    private String synopsis;

    private String description;

    private List<QuoteViewDTO> quoteList;
}
