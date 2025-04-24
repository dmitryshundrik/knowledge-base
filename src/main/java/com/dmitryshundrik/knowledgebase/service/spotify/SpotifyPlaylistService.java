package com.dmitryshundrik.knowledgebase.service.spotify;

import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.SimplifiedPlaylistObject;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.UserPlaylists;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response.PlaylistItemResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response.PlaylistResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.ArtistObject;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.Playlist;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.PlaylistTrackObject;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request.TrackObject;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response.UserPlaylistsItemResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response.UserPlaylistsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotifyPlaylistService {

    private static final String SPOTIFY_API_PLAYLIST_BY_ID = "https://api.spotify.com/v1/playlists/";

    private static final String SPOTIFY_API_MY_PLAYLISTS = "https://api.spotify.com/v1/me/playlists";

    private static final Logger logger = LoggerFactory.getLogger(SpotifyPlaylistService.class);

    private final SpotifyIntegrationService spotifyIntegrationService;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();


    public PlaylistResponse getPlaylist(String playlistId, Boolean orderByRelease) {
        PlaylistResponse playlistResponse = new PlaylistResponse();
        try {
            HttpRequest getPlaylistRequest = HttpRequest.newBuilder()
                    .uri(URI.create(SPOTIFY_API_PLAYLIST_BY_ID + playlistId))
                    .GET()
                    .header("Authorization", "Bearer " + spotifyIntegrationService.getAccessToken())
                    .build();
            HttpResponse<String> response = httpClient.send(getPlaylistRequest, HttpResponse.BodyHandlers.ofString());
            if (!"".equals(response.body())) {
                Playlist playlist = objectMapper.readValue(response.body(), Playlist.class);
                playlistResponse = createPlaylistResponse(playlist, orderByRelease);
            }
        } catch (Exception e) {
            logger.error("Unexpected error while fetching playlist", e);
        }
        return playlistResponse;
    }

    private PlaylistResponse createPlaylistResponse(Playlist playlist, Boolean orderByRelease) {
        List<PlaylistItemResponse> playlistItemResponseList = new ArrayList<>();
        List<PlaylistTrackObject> items = playlist.getTracks().getItems();
        for (PlaylistTrackObject playlistTrackObject : items) {
            PlaylistItemResponse playlistItemResponse = new PlaylistItemResponse();
            TrackObject track = playlistTrackObject.getTrack();
            playlistItemResponse.setName(track.getName());
            playlistItemResponse.setAlbum(track.getAlbum().getName());
            playlistItemResponse.setAlbumReleaseDate(track.getAlbum().getReleaseDate());
            playlistItemResponse.setArtist(StringUtils.join(track.getArtists().stream()
                    .map(ArtistObject::getName).collect(Collectors.toList()), ", "));
            playlistItemResponseList.add(playlistItemResponse);
        }

        if (orderByRelease) {
            playlistItemResponseList.sort((item1, item2) -> {
                try {
                    LocalDate date1 = parseReleaseDate(item1.getAlbumReleaseDate());
                    LocalDate date2 = parseReleaseDate(item2.getAlbumReleaseDate());
                    return date1.compareTo(date2);
                } catch (DateTimeParseException e) {
                    return item1.getAlbumReleaseDate() == null ? 1 : -1;
                }
            });
        }

        PlaylistResponse playlistResponse = new PlaylistResponse();
        playlistResponse.setId(playlist.getId());
        playlistResponse.setName(playlist.getName());
        playlistResponse.setItems(playlistItemResponseList);
        playlistResponse.setUri(playlist.getUri());
        return playlistResponse;
    }

    private LocalDate parseReleaseDate(String releaseDate) {
        if (releaseDate == null) {
            throw new DateTimeParseException("Release date is null", "", 0);
        }
        if (releaseDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return LocalDate.parse(releaseDate);
        } else if (releaseDate.matches("\\d{4}")) {
            return LocalDate.parse(releaseDate + "-01-01");
        } else {
            throw new DateTimeParseException("Invalid date format: " + releaseDate, releaseDate, 0);
        }
    }

    public UserPlaylistsResponse getUserPlaylists() {
        UserPlaylistsResponse userPlaylistsResponse = new UserPlaylistsResponse();
        try {
            HttpRequest getPlaylistRequest = HttpRequest.newBuilder()
                    .uri(URI.create(SPOTIFY_API_MY_PLAYLISTS))
                    .GET()
                    .header("Authorization", "Bearer " + spotifyIntegrationService.getAccessToken())
                    .build();
            HttpResponse<String> response = httpClient.send(getPlaylistRequest, HttpResponse.BodyHandlers.ofString());
            if (!"".equals(response.body())) {
                UserPlaylists userPlaylists = objectMapper.readValue(response.body(), UserPlaylists.class);
                userPlaylistsResponse = createUserPlaylistsResponse(userPlaylists);
            }
        } catch (Exception e) {
            logger.error("Unexpected error while fetching playlists", e);
        }
        return userPlaylistsResponse;
    }

    private UserPlaylistsResponse createUserPlaylistsResponse(UserPlaylists userPlaylists) {
        List<UserPlaylistsItemResponse> userPlaylistsItemResponses = new ArrayList<>();
        List<SimplifiedPlaylistObject> items = userPlaylists.getItems();
        for (SimplifiedPlaylistObject playlistObject : items) {
            UserPlaylistsItemResponse userPlaylistsItemResponse = new UserPlaylistsItemResponse();
            userPlaylistsItemResponse.setId(playlistObject.getId());
            userPlaylistsItemResponse.setName(playlistObject.getName());
            userPlaylistsItemResponse.setDescription(playlistObject.getDescription());
            userPlaylistsItemResponse.setIsPublic(playlistObject.getIsPublic());
            userPlaylistsItemResponses.add(userPlaylistsItemResponse);
        }
        UserPlaylistsResponse userPlaylistsResponse = new UserPlaylistsResponse();
        userPlaylistsResponse.setTotal(userPlaylists.getTotal());
        userPlaylistsResponse.setItems(userPlaylistsItemResponses);
        return userPlaylistsResponse;
    }
}
