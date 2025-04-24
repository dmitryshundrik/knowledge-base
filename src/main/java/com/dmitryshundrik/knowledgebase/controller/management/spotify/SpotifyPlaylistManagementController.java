package com.dmitryshundrik.knowledgebase.controller.management.spotify;

import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.PlaylistId;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.PlaylistResponse;
import com.dmitryshundrik.knowledgebase.service.client.impl.SpotifyPlaylistService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.ap.internal.util.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SpotifyPlaylistManagementController {

    private final SpotifyPlaylistService spotifyPlaylistService;

    @GetMapping("management/spotify/playlists")
    public String getPlaylists(Model model) {
        model.addAttribute("userPlaylists", spotifyPlaylistService.getUserPlaylists());
        model.addAttribute("playlistId", new PlaylistId());
        return "management/tools/spotify/spotify-playlist-all";
    }

    @PostMapping("management/spotify/playlists")
    public String postPlaylistsTracks(@ModelAttribute("playlistId") PlaylistId playlistId, Model model) {
        String id = playlistId.getId();
        if (Strings.isEmpty(id)) {
            return "redirect:/management/spotify/playlists";
        }
        PlaylistResponse playlist = spotifyPlaylistService.getPlaylist(id, playlistId.getOrderByRelease());
        model.addAttribute("userPlaylists", spotifyPlaylistService.getUserPlaylists());
        model.addAttribute("playlist", playlist);
        return "management/tools/spotify/spotify-playlist-all";
    }
}
