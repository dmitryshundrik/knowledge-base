package com.dmitryshundrik.knowledgebase.model.entity.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrentEventInfo {

    private String personNickname;

    private String personLink;

    private String personImage;

    private String date;

    private Integer month;

    private Integer day;

    private String dateType;

    private String occupation;
}
