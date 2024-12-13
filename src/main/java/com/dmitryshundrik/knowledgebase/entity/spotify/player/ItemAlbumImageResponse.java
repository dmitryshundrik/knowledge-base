package com.dmitryshundrik.knowledgebase.entity.spotify.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemAlbumImageResponse {

    @JsonProperty("url")
    private String url;
}
