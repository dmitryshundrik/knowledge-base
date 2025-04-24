package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topartists;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Artist {

    @JsonProperty("name")
    private String name;

    @JsonProperty("playcount")
    private String playcount;

    @JsonProperty("url")
    private String url;

    @JsonProperty("mbid")
    private String mbid;

    @JsonProperty("streamable")
    private String streamable;

    @JsonProperty("@attr")
    private ArtistAttributes attributes;
}
