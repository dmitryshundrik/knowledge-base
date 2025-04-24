package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopAlbums {

    @JsonProperty("album")
    private List<Album> albums;

    @JsonProperty("@attr")
    private TopAlbumsAttributes attributes;
}
