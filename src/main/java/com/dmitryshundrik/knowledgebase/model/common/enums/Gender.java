package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum Gender {

    MALE("Мужчина"), FEMALE("Женщина"), NON_BINARY("Небинарная персона");

    private final String label;

    Gender(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
