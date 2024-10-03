package com.dmitryshundrik.knowledgebase.util;

public class SlugFormatter {

    public static String slugFormatter(String slug) {
        String formattedSlug;
        formattedSlug = slug.trim().replace(" ", "-");
        formattedSlug = formattedSlug.toLowerCase();
        return formattedSlug;
    }
}
