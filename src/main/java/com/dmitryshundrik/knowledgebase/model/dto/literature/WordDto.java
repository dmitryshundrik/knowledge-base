package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WordDto {

    private String id;

    private String created;

    private String writerNickname;

    private String writerSlug;

    private String title;

    private String description;
}
