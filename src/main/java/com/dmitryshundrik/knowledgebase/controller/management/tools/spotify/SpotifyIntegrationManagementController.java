package com.dmitryshundrik.knowledgebase.controller.management.tools.spotify;

import com.dmitryshundrik.knowledgebase.service.tools.spotify.SpotifyIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;


@Controller
public class SpotifyIntegrationManagementController {

    @Autowired
    private SpotifyIntegrationService integrationService;

    @GetMapping("/management/spotify-integration/login")
    public void getSpotifyIntegrationLogin(HttpServletResponse response) throws URISyntaxException, IOException {
        String urlAuthorize = integrationService.getURLAuthorize();
        response.sendRedirect(urlAuthorize);
    }

    @GetMapping("/management/spotify-integration/login/get-access-token")
    public String getSpotifyIntegrationAccessToken(@RequestParam("code") String code, Model model) throws IOException, InterruptedException {
        integrationService.getAccessToken(code);
        return "redirect:/management/spotify-integration";
    }

    @GetMapping("/management/spotify-integration")
    public String getSpotifyIntegrationPage(HttpServletResponse response) throws IOException {
        if (integrationService.getAccessToken() == null) {
            response.sendRedirect("/management/spotify-integration/login");
        }
        return "management/tools/spotify/spotify-integration-page";
    }

    @PostMapping("/management/spotify-integration/current-song-name")
    @ResponseBody
    public String getCurrentSongName() throws IOException, InterruptedException {
        return integrationService.getCurrentSong();
    }

}
