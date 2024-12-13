package com.dmitryshundrik.knowledgebase.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuoteCreateEditDTO {

    private String id;

    private String writerNickname;

    private String writerSlug;

    private String proseId;

    private String publication;

    private String location;

    private Integer page;

    private String description;

    private String descriptionHtml;
}
