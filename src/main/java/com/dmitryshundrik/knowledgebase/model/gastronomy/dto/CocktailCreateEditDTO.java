package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class CocktailCreateEditDTO {

    private Instant created;

    private String slug;

    private String title;

    private String about;

    private String ingredients;

    private String method;

    public CocktailCreateEditDTO() {

    }

}
