package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopArtists {

    @JsonProperty("artist")
    private List<Artist> artists;

    @JsonProperty("@attr")
    private TopArtistsAttributes attributes;
}
