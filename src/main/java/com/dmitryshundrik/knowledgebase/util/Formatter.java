package com.dmitryshundrik.knowledgebase.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

    private static final String PATTERN_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String instantFormatter(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }
}
