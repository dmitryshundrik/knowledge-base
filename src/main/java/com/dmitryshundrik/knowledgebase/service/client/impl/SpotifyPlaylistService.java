package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.mapper.client.PlaylistMapper;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.SimplifiedPlaylistObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.UserPlaylists;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.PlaylistItemResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.PlaylistResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.ArtistObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.Playlist;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.PlaylistTrackObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.TrackObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.UserPlaylistsItemResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.UserPlaylistsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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

import static com.dmitryshundrik.knowledgebase.util.Constants.INVALID_DATE_FORMAT;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotifyPlaylistService {

    private static final String SPOTIFY_API_PLAYLIST_BY_ID = "https://api.spotify.com/v1/playlists/";

    private static final String SPOTIFY_API_MY_PLAYLISTS = "https://api.spotify.com/v1/me/playlists";

    private final SpotifyIntegrationService spotifyIntegrationService;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final PlaylistMapper playlistMapper;

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
            log.error("Unexpected error while fetching playlists", e);
        }
        return userPlaylistsResponse;
    }

    private UserPlaylistsResponse createUserPlaylistsResponse(UserPlaylists userPlaylists) {
        List<UserPlaylistsItemResponse> userPlaylistsItemResponses = new ArrayList<>();
        List<SimplifiedPlaylistObject> items = userPlaylists.getItems();
        for (SimplifiedPlaylistObject playlistObject : items) {
            UserPlaylistsItemResponse userPlaylistsItemResponse = playlistMapper.toUserPlaylistsItemResponse(playlistObject);
            userPlaylistsItemResponses.add(userPlaylistsItemResponse);
        }
        UserPlaylistsResponse userPlaylistsResponse = new UserPlaylistsResponse();
        userPlaylistsResponse.setTotal(userPlaylists.getTotal());
        userPlaylistsResponse.setItems(userPlaylistsItemResponses);
        return userPlaylistsResponse;
    }

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
            log.error("Unexpected error while fetching playlist", e);
        }
        return playlistResponse;
    }

    private PlaylistResponse createPlaylistResponse(Playlist playlist, Boolean orderByRelease) {
        List<PlaylistItemResponse> playlistItemResponseList = new ArrayList<>();
        List<PlaylistTrackObject> items = playlist.getTracks().getItems();
        for (PlaylistTrackObject playlistTrackObject : items) {
            TrackObject trackObject = playlistTrackObject.getTrack();
            PlaylistItemResponse playlistItemResponse = playlistMapper.toPlaylistItemResponse(trackObject);
            playlistItemResponse.setArtist(StringUtils.join(trackObject.getArtists().stream()
                    .map(ArtistObject::getName).collect(Collectors.toList()), ", "));
            playlistItemResponseList.add(playlistItemResponse);
        }

        orderPlaylistSongs(playlistItemResponseList, orderByRelease);

        PlaylistResponse playlistResponse = new PlaylistResponse();
        playlistResponse.setId(playlist.getId());
        playlistResponse.setName(playlist.getName());
        playlistResponse.setItems(playlistItemResponseList);
        playlistResponse.setUri(playlist.getUri());
        return playlistResponse;
    }

    private void orderPlaylistSongs(List<PlaylistItemResponse> playlistItemResponseList, Boolean orderByRelease) {
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
            throw new DateTimeParseException(INVALID_DATE_FORMAT.formatted(releaseDate), releaseDate, 0);
        }
    }
}
