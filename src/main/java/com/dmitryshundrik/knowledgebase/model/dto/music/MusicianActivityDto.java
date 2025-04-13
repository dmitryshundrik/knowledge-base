package com.dmitryshundrik.knowledgebase.model.dto.music;

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
