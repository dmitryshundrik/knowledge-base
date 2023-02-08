package com.dmitryshundrik.knowledgebase.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/management")
public class ArchiveManagementController {

    @GetMapping()
    public String getArchiveManagementPage() {
        return "management/archive-management";
    }

}
