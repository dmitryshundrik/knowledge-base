package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SettingCreateEditDTO {

    private String id;

    private String name;

    private String value;

}
