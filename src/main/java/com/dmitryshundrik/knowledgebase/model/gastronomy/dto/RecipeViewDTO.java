package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeViewDTO {

    private String created;

    private String slug;

    private String title;

    private Country country;

    private String about;

    private String ingredients;

    private String method;

}
