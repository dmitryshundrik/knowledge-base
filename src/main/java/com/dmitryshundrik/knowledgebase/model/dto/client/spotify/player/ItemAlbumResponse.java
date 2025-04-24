package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemAlbumResponse {

    @JsonProperty("images")
    private List <ItemAlbumImageResponse> itemAlbumImageResponseList;

    @JsonProperty("name")
    private String name;
}
