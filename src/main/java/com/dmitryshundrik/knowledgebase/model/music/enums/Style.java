package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Style {

    SACRED_MUSIC("Духовная музыка", "sacred-music"),
    SECULAR_MUSIC("Светская музыка", "secular-music"),

    INSTRUMENTAL_MUSIC("Инструментальные произведения", "instrumental-music"),
    VOCAL_MUSIC("Вокальные произведения", "vocal-music"),

    THEATRE_MUSIC("Театральная музыка", "theatre-music"),

    CONTEMPORARY("Cовременная музыка", "contemporary");

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
}
