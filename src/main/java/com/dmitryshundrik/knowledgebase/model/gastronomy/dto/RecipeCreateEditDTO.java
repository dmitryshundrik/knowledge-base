package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class RecipeCreateEditDTO {

    private Instant created;

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private Country country;

    private String about;

    private String ingredients;

    private String method;

    private List<ImageDTO> imageList;

    public RecipeCreateEditDTO() {

    }

}
