package com.dmitryshundrik.knowledgebase.model.dto.gastronomy;

import com.dmitryshundrik.knowledgebase.model.dto.core.ImageDto;
import com.dmitryshundrik.knowledgebase.model.enums.Country;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecipeViewDto {

    private String created;

    private String slug;

    private String title;

    private Country country;

    private String about;

    private String ingredients;

    private String method;

    private List<ImageDto> imageList;
}
