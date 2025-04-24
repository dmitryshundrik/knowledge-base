package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Album {

    @JsonProperty("name")
    private String name;

    @JsonProperty("playcount")
    private String playcount;

    @JsonProperty("url")
    private String url;

    @JsonProperty("mbid")
    private String mbid;

    @JsonProperty("artist")
    private Artist artist;

    @JsonProperty("@attr")
    private AlbumAttributes attributes;
}
