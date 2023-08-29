package com.dmitryshundrik.knowledgebase.model.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SettingViewDTO {

    String id;

    String created;

    String name;

    String value;

    public SettingViewDTO() {

    }

}
