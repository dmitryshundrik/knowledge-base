package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum Period {

    MEDIEVAL("Средневековье", "medieval"),
    RENAISSANCE("Возрождение", "renaissance"),
    BAROQUE("Барокко", "baroque"),
    CLASSICAL("Класссицизм", "classical"),
    ROMANTIC("Романтизм", "romantic"),
    MODERNISM("Модернизм", "modernism"),
    XX_CENTURY_MUSIC("XX век", "xx-century-music"),
    XXI_CENTURY_MUSIC("XXI век", "xxi-century-music");

    private final String label;

    private final String slug;

    Period(String label, String slug) {
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
