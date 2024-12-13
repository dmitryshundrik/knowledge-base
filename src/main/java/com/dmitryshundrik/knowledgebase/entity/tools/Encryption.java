package com.dmitryshundrik.knowledgebase.entity.tools;

import com.dmitryshundrik.knowledgebase.util.enums.EncryptionType;
import com.dmitryshundrik.knowledgebase.util.enums.OperationType;
import lombok.Data;

@Data
public class Encryption {

    private String text;

    private OperationType operationType;

    private EncryptionType encryptionType;

    private String key;

    private String result;
}
