package com.dmitryshundrik.knowledgebase.controller.management;

import com.dmitryshundrik.knowledgebase.service.music.AlbumService;
import com.dmitryshundrik.knowledgebase.service.music.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/management")
public class DatabaseManagementController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CompositionService compositionService;

    @GetMapping()
    public String getManagementPage() {
        return "management/databaseManagement";
    }

    @GetMapping("/albums")
    public String getAlbumsManagement(Model model) {
        model.addAttribute("albums", albumService.getAllAlbums());
        return "management/albumsManagement";
    }

    @GetMapping("/compositions")
    public String getCompositionsManagement(Model model) {
        model.addAttribute("compositions", compositionService.getAllCompositions());
        return "management/compositionsManagement";
    }
}
