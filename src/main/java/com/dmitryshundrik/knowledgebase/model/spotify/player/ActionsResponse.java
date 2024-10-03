package com.dmitryshundrik.knowledgebase.model.spotify.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionsResponse {

    @JsonProperty("disallows")
    private ActionsDisallowsResponse actionsDisallowsResponse;
}
