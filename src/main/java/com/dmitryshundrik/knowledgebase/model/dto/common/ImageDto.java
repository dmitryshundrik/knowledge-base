package com.dmitryshundrik.knowledgebase.model.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private String id;

    private String created;

    private String slug;

    private String title;

    private String description;

    private String data;
}
