package com.dmitryshundrik.knowledgebase.model.music.enums;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum Style {

    SACRED_MUSIC("Духовная музыка", "sacred-music"),
    SECULAR_MUSIC("Светская музыка", "secular-music"),
    INSTRUMENTAL_MUSIC("Инструментальные произведения", "instrumental-music"),
    VOCAL_MUSIC("Вокальные произведения", "vocal-music"),
    THEATRE_MUSIC("Театральная музыка", "theatre-music"),
    CONTEMPORARY("Современная музыка", "contemporary");

    private final String label;

    private final String slug;

    Style(String label, String slug) {
        this.label = label;
        this.slug = slug;
    }

    public String getLabel() {
        return label;
    }

    public String getSlug() {
        return slug;
    }

    public static List<Style> getSortedValues() {
        return Arrays.stream(Style.values()).sorted(Comparator.comparing(Style::getLabel)).collect(Collectors.toList());
    }
}
