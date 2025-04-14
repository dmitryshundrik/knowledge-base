package com.dmitryshundrik.knowledgebase.util;

import lombok.experimental.UtilityClass;
import java.time.LocalDateTime;

@UtilityClass
public class DateTimeGetter {

    public static String getCurrentYear() {
        return String.valueOf(LocalDateTime.now().getYear());
    }
}
