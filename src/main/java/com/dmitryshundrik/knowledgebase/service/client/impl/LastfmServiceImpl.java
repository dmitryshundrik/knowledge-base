package com.dmitryshundrik.knowledgebase.service.client.impl;

import com.dmitryshundrik.knowledgebase.client.LastFmClient;
import com.dmitryshundrik.knowledgebase.exception.WeatherServiceException;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbums;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbumsResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtists;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtistsResponse;
import com.dmitryshundrik.knowledgebase.service.client.LastfmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.dmitryshundrik.knowledgebase.util.Constants.GETTING_TOP_ALBUMS_FAIL_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.GETTING_TOP_ARTISTS_FAIL_MESSAGE;
import static com.dmitryshundrik.knowledgebase.util.Constants.TOP_ALBUM_CACHE;
import static com.dmitryshundrik.knowledgebase.util.Constants.TOP_ARTIST_CACHE;

@Service
@RequiredArgsConstructor
@Slf4j
public class LastfmServiceImpl implements LastfmService {

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
    @Cacheable(value = TOP_ARTIST_CACHE, key = "#root.methodName")
    public TopArtists getTopArtists() {
        return processTopArtists();
    }

    @Override
    @Cacheable(value = TOP_ALBUM_CACHE, key = "#root.methodName")
    public TopAlbums getTopAlbums() {
        return processTopAlbums();
    }

    @Override
    @CachePut(value = TOP_ARTIST_CACHE, key = "'getTopArtists'")
    public TopArtists processTopArtists() {
        return getTopArtistFromClient(period, limit, pade).getTopArtists();
    }

    @Override
    @CachePut(value = TOP_ALBUM_CACHE, key = "'getTopAlbums'")
    public TopAlbums processTopAlbums() {
        return getTopAlbumsFromClient(period, limit, pade).getTopAlbums();
    }

    private TopArtistsResponse getTopArtistFromClient(String period, String limit, String page) {
        try {
            log.info("Requesting top artists for user: {}", user);
            return lastFmClient
                    .getTopArtists(user, period, limit, page, apiKey);
        } catch (Exception e) {
            log.error("Error while fetching lastfm data: {}", e.getMessage(), e);
            throw new WeatherServiceException(GETTING_TOP_ARTISTS_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }

    private TopAlbumsResponse getTopAlbumsFromClient(String period, String limit, String page) {
        try {
            log.info("Requesting top albums for user: {}", user);
            return lastFmClient
                    .getTopAlbums(user, period, limit, page, apiKey);
        } catch (Exception e) {
            log.error("Error while fetching lastfm data: {}", e.getMessage(), e);
            throw new WeatherServiceException(GETTING_TOP_ALBUMS_FAIL_MESSAGE.formatted(e.getMessage()));
        }
    }
}
