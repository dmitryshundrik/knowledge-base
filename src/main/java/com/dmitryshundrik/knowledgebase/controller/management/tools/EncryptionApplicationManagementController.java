package com.dmitryshundrik.knowledgebase.controller.management.tools;

import com.dmitryshundrik.knowledgebase.model.entity.tools.Encryption;
import com.dmitryshundrik.knowledgebase.model.enums.EncryptionType;
import com.dmitryshundrik.knowledgebase.model.enums.OperationType;
import com.dmitryshundrik.knowledgebase.service.tools.EncryptionApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class EncryptionApplicationManagementController {

    private final EncryptionApplicationService encryptionApplicationService;

    @GetMapping("/management/encryption-application")
    public String getEncryptionApplication(Model model) {
        model.addAttribute("encryption", new Encryption());
        model.addAttribute("operationTypes", OperationType.values());
        model.addAttribute("encryptionTypes", EncryptionType.values());
        return "management/tools/encryption-application";
    }

    @PostMapping("/management/encryption-application")
    public String postEncryptionApplication(@ModelAttribute("encryption") Encryption encryption, Model model) {
        if (encryption.getOperationType().equals(OperationType.ENCRYPT)) {
            String result = encryptionApplicationService.encrypt(encryption);
            encryption.setResult(result);
        } else if (encryption.getOperationType().equals(OperationType.DECRYPT)) {
            String result = encryptionApplicationService.decrypt(encryption);
            encryption.setResult(result);
        }
        model.addAttribute("encryption", encryption);
        model.addAttribute("operationTypes", OperationType.values());
        model.addAttribute("encryptionTypes", EncryptionType.values());
        return "management/tools/encryption-application";
    }
}
