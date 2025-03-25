package com.dmitryshundrik.knowledgebase.controller.management.spotify;

import com.dmitryshundrik.knowledgebase.service.spotify.SpotifyIntegrationService;
import com.dmitryshundrik.knowledgebase.service.spotify.SpotifyPlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class SpotifyPlayerManagementController {

    private final SpotifyIntegrationService spotifyIntegrationService;

    private final SpotifyPlayerService spotifyPlayerService;

    @GetMapping("/management/spotify/player")
    public String getSpotifyPlayerPage(HttpServletResponse response) throws IOException {
        if (spotifyIntegrationService.getAccessToken() == null) {
            response.sendRedirect("/management/spotify-integration/login");
        }
        return "management/tools/spotify/spotify-player";
    }

    @PostMapping("/management/spotify/player/current-song-name")
    @ResponseBody
    public String getCurrentSongName() {
        return spotifyPlayerService.getCurrentSong();
    }
}
