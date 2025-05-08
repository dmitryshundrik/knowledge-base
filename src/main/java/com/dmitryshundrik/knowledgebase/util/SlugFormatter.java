package com.dmitryshundrik.knowledgebase.util;

import com.dmitryshundrik.knowledgebase.model.entity.art.Painting;
import com.dmitryshundrik.knowledgebase.model.entity.literature.Prose;
import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
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

    public static String formatAlbumSlug(Album album) {
        String musicianSlug = album.getMusician().getSlug() + "-";
        String albumSlug = album.getSlug();
        String albumYear = (album.getYear() != null ? "-" + album.getYear() : "");
        return baseFormatter("album-" + musicianSlug + albumSlug + albumYear);
    }

    public static String formatCompositionSlug(Composition composition) {
        String musicianSlug = composition.getMusician().getSlug() + "-";
        String compositionSlug = composition.getSlug();
        String compositionYear = (composition.getYear() != null ? "-" + composition.getYear() : "");
        return baseFormatter("composition-" + musicianSlug + compositionSlug + compositionYear);
    }

    public static String formatProseSlug(Prose prose) {
        String writerSlug = prose.getWriter().getSlug() + "-";
        String proseSlug = prose.getSlug();
        String proseYear = (prose.getYear() != null ? "-" + prose.getYear() : "");
        return baseFormatter("prose-" + writerSlug + proseSlug + proseYear);
    }

    public static String formatPaintingSlug(Painting painting) {
        String artistSlug = painting.getArtist().getSlug() + "-";
        String paintingSlug = painting.getSlug();
        String paintingYear = (painting.getYear2() != null ? "-" + painting.getYear2() : "");
        return baseFormatter("painting-" + artistSlug + paintingSlug + paintingYear);
    }
}
