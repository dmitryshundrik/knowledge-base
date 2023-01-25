package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Style {

    SACRED_MUSIC("Духовная музыка"),
    SECULAR_MUSIC("Светская музыка"),

    INSTRUMENTAL_MUSIC("Инструментальная музыка"),
    VOCAL_MUSIC("Вокальная музыка"),

    THEATER_MUSIC("Театральная музыка");

    private final String label;

    Style(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
