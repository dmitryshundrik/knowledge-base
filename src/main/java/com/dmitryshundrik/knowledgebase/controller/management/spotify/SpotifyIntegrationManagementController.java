package com.dmitryshundrik.knowledgebase.controller.management.spotify;

import com.dmitryshundrik.knowledgebase.service.client.impl.SpotifyIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;

@Controller
@RequiredArgsConstructor
public class SpotifyIntegrationManagementController {

    private final SpotifyIntegrationService spotifyIntegrationService;

    @GetMapping("/management/spotify-integration/login")
    public void getSpotifyIntegrationLogin(HttpServletResponse response) throws URISyntaxException, IOException {
        String urlAuthorize = spotifyIntegrationService.getURLAuthorize();
        response.sendRedirect(urlAuthorize);
    }

    @GetMapping("/management/spotify-integration/login/get-access-token")
    public String getSpotifyIntegrationAccessToken(@RequestParam("code") String code, Model model) throws IOException, InterruptedException {
        spotifyIntegrationService.getAccessToken(code);
        return "redirect:/management/spotify-integration";
    }
}
