package com.dmitryshundrik.knowledgebase.model.entity.spotify.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemResponse {

    @JsonProperty("name")
    private String songName;

    @JsonProperty("artists")
    private List<ItemArtistResponse> artists;

    @JsonProperty("album")
    private ItemAlbumResponse itemAlbumResponse;
}
