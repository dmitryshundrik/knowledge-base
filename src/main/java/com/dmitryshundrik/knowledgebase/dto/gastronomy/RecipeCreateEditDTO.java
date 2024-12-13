package com.dmitryshundrik.knowledgebase.dto.gastronomy;

import com.dmitryshundrik.knowledgebase.dto.common.ImageDTO;
import com.dmitryshundrik.knowledgebase.util.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.TITLE_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCreateEditDTO {

    private Instant created;

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    private String title;

    private Country country;

    private String about;

    private String ingredients;

    private String method;

    private List<ImageDTO> imageList;
}
