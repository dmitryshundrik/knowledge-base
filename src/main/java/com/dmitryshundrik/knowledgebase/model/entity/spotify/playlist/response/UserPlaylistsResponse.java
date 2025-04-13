package com.dmitryshundrik.knowledgebase.model.entity.spotify.playlist.response;

import lombok.Data;
import java.util.List;

@Data
public class UserPlaylistsResponse {

    private Integer total;

    private List<UserPlaylistsItemResponse> items;
}
