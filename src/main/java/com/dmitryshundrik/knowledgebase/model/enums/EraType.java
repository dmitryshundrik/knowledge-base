package com.dmitryshundrik.knowledgebase.model.enums;

import lombok.Getter;

@Getter
public enum EraType {

    BEFORE_COMMON("До нашей эры"), COMMON("Наша эра");

    private final String label;

    EraType(String label) {
        this.label = label;
    }
}
