package com.dmitryshundrik.knowledgebase.service.spotify;

import com.dmitryshundrik.knowledgebase.model.music.Album;
import com.dmitryshundrik.knowledgebase.model.music.Composition;
import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.music.Musician;
import com.dmitryshundrik.knowledgebase.model.spotify.CurrentSong;
import com.dmitryshundrik.knowledgebase.model.spotify.RefreshToken;
import com.dmitryshundrik.knowledgebase.model.spotify.player.ItemArtistResponse;
import com.dmitryshundrik.knowledgebase.model.spotify.player.PlayerResponse;
import com.dmitryshundrik.knowledgebase.repository.tools.RefreshTokenRepository;
import com.dmitryshundrik.knowledgebase.service.music.MusicianService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class SpotifyIntegrationService {

    private static final String URI_AUTHORIZE = "https://accounts.spotify.com/authorize";

    private static final String CLIENT_ID = "client_id";

    private static final String RESPONSE_TYPE = "response_type";

    private static final String SCOPE = "scope";

    private static final String REDIRECT_URI = "redirect_uri";

    private static final String GRANT_TYPE = "grant_type";

    private static final String CODE = "code";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${knowledge.base.application.url}")
    private String applicationURI;

    @Value("${knowledge.base.application.client-id}")
    private String clientID;

    @Value("${knowledge.base.application.client-secret}")
    private String clientSecret;

    @Getter
    private String accessToken;

    private String refreshToken;

    private final RefreshTokenRepository refreshTokenRepository;

    private final MusicianService musicianService;

    public SpotifyIntegrationService(RefreshTokenRepository refreshTokenRepository, MusicianService musicianService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.musicianService = musicianService;
    }

    public String getURLAuthorize() throws URISyntaxException, MalformedURLException {
        URIBuilder uriBuilder = new URIBuilder(URI_AUTHORIZE);
        uriBuilder.addParameter(CLIENT_ID, clientID);
        uriBuilder.addParameter(RESPONSE_TYPE, "code");
        uriBuilder.addParameter(SCOPE, "user-read-playback-state");
        uriBuilder.addParameter(REDIRECT_URI, applicationURI + "/management/spotify-integration/login/get-access-token");
        URL url = uriBuilder.build().toURL();
        return url.toString();
    }

    public void getAccessToken(String code) throws IOException, InterruptedException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(GRANT_TYPE, "authorization_code");
        parameters.put(CODE, code);
        parameters.put(REDIRECT_URI, applicationURI + "/management/spotify-integration/login/get-access-token");
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode node = new ObjectMapper().readTree(response.body());
        if (node.has("access_token")) {
            accessToken = node.get("access_token").asText();
        }
        if (node.has("refresh_token")) {
            refreshToken = node.get("refresh_token").asText();
            refreshTokenRepository.deleteAll();
            refreshTokenRepository.save(createRefreshTokenEntity(refreshToken));
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void refreshAccessToken() throws IOException, InterruptedException {
        if (refreshToken == null) {
            List<RefreshToken> tokenList = refreshTokenRepository.findAll();
            if (tokenList.isEmpty()) {
                System.out.println("Refresh token is null");
                return;
            } else {
                refreshToken = tokenList.get(0).getRefreshToken();
            }
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        parameters.put(CLIENT_ID, clientID);
        String form = parameters.keySet().stream()
                .map(key -> key + "=" + URLEncoder.encode(parameters.get(key), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://accounts.spotify.com/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString(form))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((clientID + ":" + clientSecret).getBytes()))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//        System.out.println(response.body());
        JsonNode node = new ObjectMapper().readTree(response.body());
        if (node.has("access_token")) {
            accessToken = node.get("access_token").asText();
        }
        if (node.has("refresh_token")) {
            refreshToken = node.get("refresh_token").asText();
            refreshTokenRepository.deleteAll();
            refreshTokenRepository.save(createRefreshTokenEntity(refreshToken));
//            System.out.println(refreshToken);
        }
    }

    public String getCurrentSong() {
        CurrentSong currentSong = new CurrentSong();
        try {
            HttpRequest getPlayerRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.spotify.com/v1/me/player"))
                    .GET()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();
            HttpResponse<String> response = httpClient.send(getPlayerRequest, HttpResponse.BodyHandlers.ofString());
            if (!"".equals(response.body())) {
                PlayerResponse playerResponse = objectMapper.readValue(response.body(), PlayerResponse.class);
                if (playerResponse.getActionsResponse() != null) {
                    if (!playerResponse.getActionsResponse().getActionsDisallowsResponse().isPausing()) {
                        currentSong = createCurrentSong(playerResponse);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            return objectMapper.writeValueAsString(currentSong);
        } catch (JsonProcessingException e) {
            return "{\"playing\": false}";
        }
    }

    public RefreshToken createRefreshTokenEntity(String refreshToken) {
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setCreated(Instant.now());
        refreshTokenEntity.setRefreshToken(refreshToken);
        return refreshTokenEntity;
    }

    public CurrentSong createCurrentSong(PlayerResponse playerResponse) {
        CurrentSong currentSong = new CurrentSong();
        String songName = playerResponse.getItem().getSongName();
        String albumName = playerResponse.getItem().getItemAlbumResponse().getName();
        String artists = StringUtils.join(playerResponse.getItem().getArtists().stream()
                .map(ItemArtistResponse::getName).collect(Collectors.toList()), ", ");
        String[] artistArray = artists.split(",");
        String musicianNickname = artistArray[0];
        Musician musicianByNickname = musicianService.getMusicianByNickname(musicianNickname);

        currentSong.setImages(playerResponse.getItem().getItemAlbumResponse().getItemAlbumImageResponseList().get(0).getUrl());
        currentSong.setArtists(musicianNickname);
        if (musicianByNickname != null) {
            currentSong.setArtistSlug("/music/musician/" + musicianByNickname.getSlug());
        }
        currentSong.setName(songName);
        currentSong.setAlbumName(albumName);

        if (musicianByNickname != null && !musicianByNickname.getCompositions().isEmpty()) {
            for (Composition composition : musicianByNickname.getCompositions()) {
                String compositionFullTitle = composition.getTitle() + " " + composition.getFeature();
                if (songName.equalsIgnoreCase(compositionFullTitle.trim())) {
                    currentSong.setMusicGenres(StringUtils.join(composition.getMusicGenres().stream()
                            .map(MusicGenre::getTitle).collect(Collectors.toList()), ", "));
                    if (composition.getYearEndRank() != null) {
                        currentSong.setYearEndRank("Лучшие композиции " + composition.getYear() + " года: " +
                                "#" + composition.getYearEndRank());
                    }
                }
            }
        }

        if (musicianByNickname != null && !musicianByNickname.getAlbums().isEmpty()) {
            for (Album album : musicianByNickname.getAlbums()) {
                String albumTitle = album.getTitle();
                if (album.getTitle().endsWith("EP")) {
                    albumTitle = album.getTitle().substring(0, album.getTitle().length() - 2);
                }
                if (albumName.toLowerCase().contains(albumTitle.trim().toLowerCase())) {
                    currentSong.setMusicGenres(StringUtils.join(album.getMusicGenres().stream()
                            .map(MusicGenre::getTitle).collect(Collectors.toList()), ", "));
                }
            }
        }
        currentSong.setPlaying(true);
        return currentSong;
    }

}
