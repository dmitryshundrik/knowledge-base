package com.dmitryshundrik.knowledgebase.model.tools;

import lombok.Data;

@Data
public class Encryption {

    private String text;

    private OperationType operationType;

    private EncryptionType encryptionType;

    private String key;

    private String result;
}
