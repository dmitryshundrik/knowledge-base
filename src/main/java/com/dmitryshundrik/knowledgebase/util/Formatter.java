package com.dmitryshundrik.knowledgebase.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Formatter {

    private static final String PATTERN_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

    private static final String PATTERN_YMD = "yyyy-MM-dd";

    public static String instantFormatterYMDHMS(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YMD_HMS).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }

    public static String instantFormatterYMD(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_YMD).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }
}
