package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request;

import lombok.Data;

@Data
public class PlaylistId {

    private String id;

    private Boolean orderByRelease;
}
