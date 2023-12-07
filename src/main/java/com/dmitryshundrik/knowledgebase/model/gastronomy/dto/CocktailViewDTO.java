package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class CocktailViewDTO {

    private String created;

    private String slug;

    private String title;

    private String about;

    private String ingredients;

    private String method;

    private List<ImageDTO> imageList;

}
