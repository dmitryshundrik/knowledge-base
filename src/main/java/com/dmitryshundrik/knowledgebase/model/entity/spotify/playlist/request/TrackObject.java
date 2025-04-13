package com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackObject {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("album")
    private AlbumObject album;

    @JsonProperty("artists")
    private List<ArtistObject> artists;
}
