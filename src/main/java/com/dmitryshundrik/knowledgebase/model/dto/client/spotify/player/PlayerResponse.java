package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.player;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerResponse {

    @JsonProperty("item")
    private ItemResponse item;

    @JsonProperty("actions")
    private ActionsResponse actionsResponse;
}
