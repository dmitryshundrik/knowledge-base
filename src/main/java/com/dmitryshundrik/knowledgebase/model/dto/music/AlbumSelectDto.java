package com.dmitryshundrik.knowledgebase.model.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlbumSelectDto {

    private String id;

    private String title;
}
