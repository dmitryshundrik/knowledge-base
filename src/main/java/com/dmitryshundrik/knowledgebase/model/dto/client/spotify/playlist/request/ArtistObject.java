package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArtistObject {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;
}
