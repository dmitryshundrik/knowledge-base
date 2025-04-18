package com.dmitryshundrik.knowledgebase.util;

import lombok.experimental.UtilityClass;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_DMY;
import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_DMY_HMS;
import static com.dmitryshundrik.knowledgebase.util.Constants.DATE_FORMAT_YMD;

@UtilityClass
public class InstantFormatter {

    public static String instantFormatterYMDHMS(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_DMY_HMS).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }

    public static String instantFormatterYMD(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_YMD).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }

    public static String instantFormatterDMY(Instant instant) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT_DMY).withZone(ZoneId.systemDefault());
        return instant == null ? "" : formatter.format(instant);
    }
}
