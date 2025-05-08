package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SlugFormatter {

    public static String baseFormatter(String slug) {
        if (slug == null) {
            return null;
        }
        return slug.trim()
                .replace(" ", "-")
                .replaceAll("[^a-zA-Z0-9-]", "")
                .toLowerCase();
    }

    public static String formatCompositionSlug(Composition composition) {
        String musicianSlug = composition.getMusician().getSlug() + "-";
        String compositionSlug = composition.getSlug();
        String compositionYear = (composition.getYear() != null ? "-" + composition.getYear() : "");
        return baseFormatter(musicianSlug + compositionSlug + compositionYear);
    }
}
