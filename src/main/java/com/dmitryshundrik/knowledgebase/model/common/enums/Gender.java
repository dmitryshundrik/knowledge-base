package com.dmitryshundrik.knowledgebase.model.common.enums;

import lombok.Getter;

@Getter
public enum Gender {

    MALE("Мужчина"), FEMALE("Женщина"), NON_BINARY("Небинарная персона"), GROUP("Группа людей");

    private final String label;

    Gender(String label) {
        this.label = label;
    }
}
