package com.dmitryshundrik.knowledgebase.model.dto.art;

import com.dmitryshundrik.knowledgebase.model.dto.common.ImageDTO;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class PaintingViewDto {

    private String created;

    private String slug;

    private String title;

    private String artistNickname;

    private String artistSlug;

    private Integer year1;

    private Integer year2;

    private String approximateYears;

    private List<PaintingStyleViewDto> paintingStyles;

    private String based;

    private Integer artistTopRank;

    private Integer allTimeTopRank;

    private String description;

    private ImageDTO image;
}
