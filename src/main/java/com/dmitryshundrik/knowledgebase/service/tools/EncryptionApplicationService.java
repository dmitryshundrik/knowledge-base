package com.dmitryshundrik.knowledgebase.service.tools;

import com.dmitryshundrik.knowledgebase.model.entity.tools.Encryption;
import com.dmitryshundrik.knowledgebase.util.enums.EncryptionType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EncryptionApplicationService {

    private int initialIndex;

    private int letters;

    public String encrypt(Encryption encryption) {
        StringBuilder encrypt = new StringBuilder();
        String text = encryption.getText().trim().toLowerCase();
        String key = encryption.getKey().trim();
        int textLength = text.length();
        int keyLength = key.length();
        if (encryption.getEncryptionType().equals(EncryptionType.RU1072_33)) {
            initialIndex = 1072;
            letters = 33;
        } else if (encryption.getEncryptionType().equals(EncryptionType.EN97_26)) {
            initialIndex = 97;
            letters = 26;
        }
        for (int i = 0; i < textLength; i++) {
            if (text.charAt(i) >= 48 && text.charAt(i) <= 57) {
                encrypt.append(encryptNumber(text.charAt(i)));
                continue;
            }
            switch (text.charAt(i)) {
                case ' ':
                    encrypt.append(" ");
                    break;
                case '\r':
                    encrypt.append(System.lineSeparator());
                    i++;
                    break;
                default:
                    encrypt.append((char) ((text.charAt(i) + (key.charAt(i % keyLength)) - 2 * initialIndex) % letters
                            + initialIndex));
            }
        }
        return encrypt.toString();
    }

    public String decrypt(Encryption encryption) {
        StringBuilder decrypt = new StringBuilder();
        String text = encryption.getText().trim().toLowerCase();
        String key = encryption.getKey().trim();
        int textLength = encryption.getText().length();
        int keyLength = encryption.getKey().length();
        if (encryption.getEncryptionType().equals(EncryptionType.RU1072_33)) {
            initialIndex = 1072;
            letters = 33;
        } else if (encryption.getEncryptionType().equals(EncryptionType.EN97_26)) {
            initialIndex = 97;
            letters = 26;
        }
        for (int i = 0; i < textLength; i++) {
            if ((int) text.charAt(i) >= 33 && (int) text.charAt(i) <= 42) {
                decrypt.append(decryptNumber(text.charAt(i)));
                continue;
            }
            switch (text.charAt(i)) {
                case ' ':
                    decrypt.append(" ");
                    break;
                case '\r':
                    decrypt.append(System.lineSeparator());
                    i++;
                    break;
                default:
                    decrypt.append((char) (((text.charAt(i) - key.charAt(i % keyLength) + letters) % letters) + initialIndex));
            }
        }
        return decrypt.toString();
    }

    public char encryptNumber(char number) {
        return (char) (33 + ((int) number - 48));
    }

    public char decryptNumber(char number) {
        return (char) (48 + ((int) number - 33));
    }
}
