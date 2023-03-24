package com.dmitryshundrik.knowledgebase.model.common.enums;

public enum EraType {

    BEFORE_COMMON("До нашей эры"), COMMON("Наша эра");

    private final String label;

    EraType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
