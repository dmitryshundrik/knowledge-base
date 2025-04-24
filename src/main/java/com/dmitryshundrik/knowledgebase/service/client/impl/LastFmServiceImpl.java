package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.client.LastFmClient;
import com.dmitryshundrik.knowledgebase.exception.WeatherServiceException;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbums;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbumsResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtists;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtistsResponse;
import com.dmitryshundrik.knowledgebase.service.client.LastFmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.dmitryshundrik.knowledgebase.util.Constants.GETTING_TOP_ALBUMS_FAIL_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.GETTING_TOP_ARTISTS_FAIL_MESSAGE;

@Service
@RequiredArgsConstructor
@Slf4j
public class LastFmServiceImpl implements LastFmService {

    private final LastFmClient lastFmClient;

    @Value("${lastfm.api.user}")
    private String user;

    @Value("${lastfm.api.key}")
    private String apiKey;

    @Value("${lastfm.api.period}")
    private String period;

    @Value("${lastfm.api.limit}")
    private String limit;

    @Value("${lastfm.api.page}")
    private String pade;

    @Override
    public TopArtists processTopArtist() {
        return getTopArtistFromClient(period, limit, pade).getTopArtists();
    }

    private TopArtistsResponse getTopArtistFromClient(String period, String limit, String page) {
        try {
            log.info("Requesting top artist for user: {}", user);
            TopArtistsResponse topArtistsResponse = lastFmClient
                    .getTopArtists(user, period, limit, page, apiKey);
            TopArtists topArtists = topArtistsResponse.getTopArtists();
            log.info("Received response: {}", topArtists);
            return topArtistsResponse;
        } catch (Exception e) {
            log.error("Error while fetching weather data: {}", e.getMessage(), e);
            throw new WeatherServiceException(GETTING_TOP_ARTISTS_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }

    @Override
    public TopAlbums processTopAlbums() {
        return getTopAlbumsFromClient(period, limit, pade).getTopAlbums();
    }

    private TopAlbumsResponse getTopAlbumsFromClient(String period, String limit, String page) {
        try {
            log.info("Requesting top albums for user: {}", user);
            TopAlbumsResponse topAlbumsResponse = lastFmClient
                    .getTopAlbums(user, period, limit, page, apiKey);
            TopAlbums topAlbums = topAlbumsResponse.getTopAlbums();
            log.info("Received response: {}", topAlbums);
            return topAlbumsResponse;
        } catch (Exception e) {
            log.error("Error while fetching weather data: {}", e.getMessage(), e);
            throw new WeatherServiceException(GETTING_TOP_ALBUMS_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }
}
