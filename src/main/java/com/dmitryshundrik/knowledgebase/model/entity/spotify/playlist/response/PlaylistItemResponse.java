package com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response;

import lombok.Data;

@Data
public class PlaylistItemResponse {

    private String name;

    private String album;

    private String albumReleaseDate;

    private String artist;
}
