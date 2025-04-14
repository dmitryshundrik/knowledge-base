package com.dmitryshundrik.knowledgebase.model.dto.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SettingViewDTO {

    String id;

    String created;

    String name;

    String value;
}
