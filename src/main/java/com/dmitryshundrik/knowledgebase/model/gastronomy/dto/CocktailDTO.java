package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CocktailDTO {

    private Instant created;

    private String slug;

    private String title;

    private Country country;

    private String about;

    private String ingredients;

    private String method;

}
