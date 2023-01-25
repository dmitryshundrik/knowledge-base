package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Period {

    MEDIEVAL("Средневековье"),
    RENAISSANCE("Возрождение"),
    EARLY_BAROQUE("Ранее барокко"),
    BAROQUE("Барокко"),
    CLASSICAL("Класссицизм"),
    ROMANTIC("Романтизм"),
    MODERNISM("Модернизм");

    private final String label;

    Period(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
