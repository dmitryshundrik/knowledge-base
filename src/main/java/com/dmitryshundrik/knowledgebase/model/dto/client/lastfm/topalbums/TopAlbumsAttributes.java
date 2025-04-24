package com.dmitryshundrik.knowledgebase.model.dto.client.lastfm.topalbums;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TopAlbumsAttributes {

    @JsonProperty("user")
    private String user;

    @JsonProperty("page")
    private String page;

    @JsonProperty("perPage")
    private String perPage;

    @JsonProperty("totalPages")
    private String totalPages;

    @JsonProperty("total")
    private String total;
}
