package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MusicianSelectDTO {

    private String id;

    private String slug;

    private String nickName;

}
