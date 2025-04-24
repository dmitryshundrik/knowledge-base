package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopArtistsResponse {

    @JsonProperty("topartists")
    private TopArtists topArtists;
}

