package com.dmitryshundrik.knowledgebase.service.spotify;

import com.dmitryshundrik.knowledgebase.model.entity.music.Album;
import com.dmitryshundrik.knowledgebase.model.entity.music.Composition;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.Musician;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.CurrentSong;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.player.ItemAlbumResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.player.ItemArtistResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.player.ItemResponse;
import com.dmitryshundrik.knowledgebase.model.entity.spotify.player.PlayerResponse;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpotifyPlayerService {

    private static final String SPOTIFY_API_BASE_URL = "https://api.spotify.com/v1/me/player";

    private static final Logger logger = LoggerFactory.getLogger(SpotifyPlayerService.class);

    private final SpotifyIntegrationService spotifyIntegrationService;

    private final MusicianService musicianService;

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String getCurrentSong() {
        CurrentSong currentSong = new CurrentSong();
        try {
            HttpRequest getPlayerRequest = HttpRequest.newBuilder()
                    .uri(URI.create(SPOTIFY_API_BASE_URL))
                    .GET()
                    .header("Authorization", "Bearer " + spotifyIntegrationService.getAccessToken())
                    .build();
            HttpResponse<String> response = httpClient.send(getPlayerRequest, HttpResponse.BodyHandlers.ofString());
            if (!"".equals(response.body())) {
                PlayerResponse playerResponse = objectMapper.readValue(response.body(), PlayerResponse.class);
                if (playerResponse.getActionsResponse() != null && !playerResponse.getActionsResponse().getActionsDisallowsResponse().isPausing()) {
                    currentSong = createCurrentSong(playerResponse);
                }
            }
        } catch (Exception e) {
            logger.error("Unexpected error while fetching current song", e);
        }
        try {
            return objectMapper.writeValueAsString(currentSong);
        } catch (JsonProcessingException e) {
            return "{\"playing\": false}";
        }
    }

    public CurrentSong createCurrentSong(PlayerResponse playerResponse) {
        CurrentSong currentSong = new CurrentSong();
        ItemResponse item = playerResponse.getItem();
        ItemAlbumResponse itemAlbumResponse = item.getItemAlbumResponse();

        currentSong.setName(item.getSongName());
        currentSong.setAlbumName(itemAlbumResponse.getName());
        currentSong.setImages(itemAlbumResponse.getItemAlbumImageResponseList().get(0).getUrl());
        currentSong.setPlaying(true);

        String artists = item.getArtists().stream()
                .map(ItemArtistResponse::getName)
                .collect(Collectors.joining(", "));
        String musicianNickname = artists.split(",")[0].trim();
        currentSong.setArtists(musicianNickname);

        Musician musician = musicianService.getMusicianByNickname(musicianNickname);
        if (musician == null) {
            return currentSong;
        }

        currentSong.setArtistSlug("/music/musician/" + musician.getSlug());
        setMusicGenres(currentSong, musician);
        return currentSong;
    }

    private void setMusicGenres(CurrentSong currentSong, Musician musician) {
        String songName = currentSong.getName().trim();
        String albumName = currentSong.getAlbumName().toLowerCase();

        musician.getCompositions().stream()
                .filter(comp -> matchesCompositionTitle(comp, songName))
                .findFirst()
                .ifPresent(comp -> {
                    setGenresFromComposition(currentSong, comp);
                    setYearEndRank(currentSong, comp);
                });

        if (currentSong.getMusicGenres() == null) {
            musician.getAlbums().stream()
                    .filter(album -> matchesAlbumTitle(album, albumName))
                    .findFirst()
                    .ifPresent(album -> setGenresFromAlbum(currentSong, album));
        }
    }

    private boolean matchesCompositionTitle(Composition comp, String songName) {
        return (comp.getTitle() + " " + comp.getFeature()).trim()
                .equalsIgnoreCase(songName);
    }

    private boolean matchesAlbumTitle(Album album, String albumName) {
        String title = album.getTitle().replace("EP", "").trim().toLowerCase();
        return albumName.contains(title);
    }

    private void setGenresFromComposition(CurrentSong currentSong, Composition comp) {
        currentSong.setMusicGenres(comp.getMusicGenres().stream()
                .map(MusicGenre::getTitle)
                .collect(Collectors.joining(", ")));
    }

    private void setGenresFromAlbum(CurrentSong currentSong, Album album) {
        currentSong.setMusicGenres(album.getMusicGenres().stream()
                .map(MusicGenre::getTitle)
                .collect(Collectors.joining(", ")));
    }

    private void setYearEndRank(CurrentSong currentSong, Composition comp) {
        if (comp.getYearEndRank() != null) {
            currentSong.setYearEndRank(String.format(
                    "Лучшие композиции %d года: #%d",
                    comp.getYear(),
                    comp.getYearEndRank()
            ));
        }
    }
}
