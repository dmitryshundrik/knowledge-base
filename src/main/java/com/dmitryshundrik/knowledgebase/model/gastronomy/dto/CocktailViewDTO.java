package com.dmitryshundrik.knowledgebase.model.gastronomy.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CocktailViewDTO {

    private String created;

    private String slug;

    private String title;

    private String about;

    private String ingredients;

    private String method;

    private List<ImageDTO> imageList;

}
