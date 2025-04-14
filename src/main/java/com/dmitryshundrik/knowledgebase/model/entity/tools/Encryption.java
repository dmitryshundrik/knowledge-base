package com.dmitryshundrik.knowledgebase.model.entity.tools;

import com.dmitryshundrik.knowledgebase.model.enums.EncryptionType;
import com.dmitryshundrik.knowledgebase.model.enums.OperationType;
import lombok.Data;

@Data
public class Encryption {

    private String text;

    private OperationType operationType;

    private EncryptionType encryptionType;

    private String key;

    private String result;
}
