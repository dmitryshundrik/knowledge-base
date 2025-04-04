package com.dmitryshundrik.knowledgebase.dto.gastronomy;

import com.dmitryshundrik.knowledgebase.dto.common.ImageDTO;
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
