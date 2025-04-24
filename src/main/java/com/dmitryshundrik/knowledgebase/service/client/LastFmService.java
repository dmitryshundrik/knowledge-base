package com.dmitryshundrik.knowledgebase.service.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums.TopAlbums;
import com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists.TopArtists;

public interface LastFmService {

    TopArtists processTopArtist();

    TopAlbums processTopAlbums();
}
