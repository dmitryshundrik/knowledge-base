package com.dmitryshundrik.knowledgebase.model.common;

import com.dmitryshundrik.knowledgebase.model.common.enums.DateType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

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
