package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPlaylists {

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("items")
    private List<SimplifiedPlaylistObject> items;
}
