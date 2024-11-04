package com.dmitryshundrik.knowledgebase.model.music.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@AllArgsConstructor
@Data
public class MusicianActivityDto {

    private Instant created;

    private String slug;

    private String nickName;
}
