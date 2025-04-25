package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@Data
@AllArgsConstructor
public class WriterEntityUpdateInfoDto {

    private Instant created;

    private String slug;

    private String nickName;
}
