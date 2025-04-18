package com.dmitryshundrik.knowledgebase.model.dto.literature;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.Instant;

@AllArgsConstructor
@Data
public class WriterEntityUpdateInfoDTO {

    private Instant created;

    private String slug;

    private String nickName;
}
