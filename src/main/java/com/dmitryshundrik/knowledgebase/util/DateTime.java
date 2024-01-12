package com.dmitryshundrik.knowledgebase.util;


import java.time.LocalDateTime;

public class DateTime {

    public static String getCurrentYear() {
        return String.valueOf(LocalDateTime.now().getYear());
    }

}
