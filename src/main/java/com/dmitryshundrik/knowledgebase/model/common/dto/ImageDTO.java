package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ImageDTO {

    private String id;

    private String created;

    private String slug;

    private String title;

    private String description;

    private String data;

    public ImageDTO() {

    }

}
