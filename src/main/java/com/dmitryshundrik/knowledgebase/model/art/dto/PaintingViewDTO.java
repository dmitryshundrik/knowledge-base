package com.dmitryshundrik.knowledgebase.model.art.dto;

import com.dmitryshundrik.knowledgebase.model.common.dto.ImageDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaintingViewDTO {

    private String created;

    private String slug;

    private String title;

    private String painterNickname;

    private String painterSlug;

    private Integer year1;

    private Integer year2;

    private String approximateYears;

    private List<PaintingStyleViewDTO> paintingStyles;

    private String based;

    private Integer painterTopRank;

    private Integer allTimeTopRank;

    private String description;

    private ImageDTO image;

}
