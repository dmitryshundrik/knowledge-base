package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuoteViewDto {

    private String id;

    private String created;

    private String writerNickname;

    private String writerSlug;

    private String proseTitle;

    private String proseSlug;

    private String publication;

    private String location;

    private Integer page;

    private String description;

    private String descriptionHtml;
}
