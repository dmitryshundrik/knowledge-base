package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.model.tools.Encryption;
import com.dmitryshundrik.knowledgebase.model.tools.EncryptionType;
import com.dmitryshundrik.knowledgebase.model.tools.OperationType;
import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.dmitryshundrik.knowledgebase.service.tools.EncryptionApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/management")
public class ManagementPageController {

    @Autowired
    private MusicianService musicianService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @Autowired
    private EncryptionApplicationService encryptionApplicationService;

    @GetMapping()
    public String getManagementPage(Model model) {
        model.addAttribute("musicianCount", musicianService.getAllMusicians().size());
        model.addAttribute("albumCount", albumService.getAllAlbums().size());
        model.addAttribute("compositionCount", compositionService.getAllCompositions().size());
        return "management/management-page";
    }

    @GetMapping("/encryption-application")
    public String getEncryptionApplication(Model model) {
        model.addAttribute("encryption", new Encryption());
        model.addAttribute("operationTypes", OperationType.values());
        model.addAttribute("encryptionTypes", EncryptionType.values());
        return "management/tools/encryption-application";
    }

    @PostMapping("/encryption-application")
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

    @GetMapping("/soty-generator")
    public String getSotyGenerator() {
        return "management/tools/soty-generator";
    }

}
