package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response;

import lombok.Data;

@Data
public class UserPlaylistsItemResponse {

    private String description;

    private String id;

    private String name;

    private Boolean isPublic;
}
