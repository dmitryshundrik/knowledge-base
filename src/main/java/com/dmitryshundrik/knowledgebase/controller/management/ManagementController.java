package com.dmitryshundrik.knowledgebase.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/management")
public class ManagementController {

    @GetMapping()
    public String getManagementPage() {
        return "management/management-page";
    }

}
