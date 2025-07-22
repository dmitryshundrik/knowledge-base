package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.RefreshToken;
import com.dmitryshundrik.knowledgebase.repository.tools.RefreshTokenRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class SpotifyIntegrationService {

    public static final String URI_AUTHORIZE = "https://accounts.spotify.com/authorize";

    public static final String CLIENT_ID = "client_id";

    public static final String RESPONSE_TYPE = "response_type";

    public static final String SCOPE = "scope";

    public static final String REDIRECT_URI = "redirect_uri";

    public static final String GRANT_TYPE = "grant_type";

    public static final String CODE = "code";

    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Value("${knowledge-base.url}")
    private String applicationURI;

    @Value("${knowledge-base.client-id}")
    private String clientID;

    @Value("${knowledge-base.client-secret}")
    private String clientSecret;

    @Getter
    private String accessToken;

    private String refreshToken;

    private final RefreshTokenRepository refreshTokenRepository;

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
        createRequestForm(parameters);
    }

    @Scheduled(fixedRate = 1000 * 60 * 30)
    public void refreshAccessToken() throws IOException, InterruptedException {
        if (refreshToken == null) {
            List<RefreshToken> tokenList = refreshTokenRepository.findAll();
            if (tokenList.isEmpty()) {
                log.info("Refresh token is null");
                return;
            } else {
                refreshToken = tokenList.get(0).getRefreshToken();
            }
        }
        Map<String, String> parameters = new HashMap<>();
        parameters.put("grant_type", "refresh_token");
        parameters.put("refresh_token", refreshToken);
        parameters.put(CLIENT_ID, clientID);
        createRequestForm(parameters);
    }

    private void createRequestForm(Map<String, String> parameters) throws IOException, InterruptedException {
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

    private RefreshToken createRefreshTokenEntity(String refreshToken) {
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setRefreshToken(refreshToken);
        return refreshTokenEntity;
    }
}
