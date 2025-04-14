package com.dmitryshundrik.knowledgebase.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SlugFormatter {

    public static String slugFormatter(String slug) {
        String formattedSlug;
        formattedSlug = slug.trim().replace(" ", "-");
        formattedSlug = formattedSlug.toLowerCase();
        return formattedSlug;
    }
}
