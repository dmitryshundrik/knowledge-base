package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum Country {

    RUSSIA("Россия", "russia"),
    FRANCE("Франция", "france");

    private final String label;

    private final String slug;


    Country(String label, String slug) {
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
