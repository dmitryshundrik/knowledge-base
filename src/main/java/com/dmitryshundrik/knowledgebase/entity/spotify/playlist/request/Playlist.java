package com.dmitryshundrik.knowledgebase.entity.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("tracks")
    private PlaylistTrack tracks;

    @JsonProperty("uri")
    private String uri;
}
