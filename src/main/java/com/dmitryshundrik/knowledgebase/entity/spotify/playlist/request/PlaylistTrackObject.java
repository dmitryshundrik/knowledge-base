package com.dmitryshundrik.knowledgebase.entity.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistTrackObject {

    @JsonProperty("added_at")
    private String addedAt;

    @JsonProperty("track")
    private TrackObject track;
}
