package com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response;

import lombok.Data;
import java.util.List;

@Data
public class UserPlaylistsResponse {

    private Integer total;

    private List<UserPlaylistsItemResponse> items;
}
