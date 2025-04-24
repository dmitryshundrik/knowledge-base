package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AlbumAttributes {

    @JsonProperty("rank")
    private String rank;
}
