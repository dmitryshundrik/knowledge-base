package com.dmitryshundrik.knowledgebase.entity.spotify.playlist.response;

import lombok.Data;
import java.util.List;

@Data
public class PlaylistResponse {

    private String id;

    private String name;

    private List<PlaylistItemResponse> items;

    private String uri;
}
