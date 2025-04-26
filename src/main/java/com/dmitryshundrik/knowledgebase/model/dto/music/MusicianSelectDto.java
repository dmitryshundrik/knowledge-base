package com.dmitryshundrik.knowledgebase.model.dto.music;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicianSelectDto {

    private String id;

    private String slug;

    private String nickName;
}
