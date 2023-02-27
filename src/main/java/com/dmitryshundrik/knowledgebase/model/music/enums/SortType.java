package com.dmitryshundrik.knowledgebase.model.music.enums;

public enum SortType {

    CATALOGUE_NUMBER("Номер каталога"), YEAR("Год"), RATING("Рейтинг");

    private final String label;

    SortType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
