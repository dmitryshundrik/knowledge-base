package com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaylistTrack {

    @JsonProperty("limit")
    private Integer limit;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("items")
    private List<PlaylistTrackObject> items;
}
