package com.dmitryshundrik.knowledgebase.model.enums;

import lombok.Getter;

@Getter
public enum SortType {

    CATALOGUE_NUMBER("Номер каталога"), YEAR("Год"), RATING("Рейтинг"), CREATED("Создан");

    private final String label;

    SortType(String label) {
        this.label = label;
    }
}
