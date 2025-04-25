package com.dmitryshundrik.knowledgebase.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingCreateEditDto {

    private String id;

    private String name;

    private String value;
}
