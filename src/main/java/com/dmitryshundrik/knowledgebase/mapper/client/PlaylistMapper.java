package com.dmitryshundrik.knowledgebase.mapper.client;

import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.SimplifiedPlaylistObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.request.TrackObject;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.PlaylistItemResponse;
import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.playlist.response.UserPlaylistsItemResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PlaylistMapper {

    public abstract UserPlaylistsItemResponse toUserPlaylistsItemResponse(SimplifiedPlaylistObject playlistObject);

    @Mapping(target = "artist", ignore = true)
    @Mapping(target = "album", source = "album.name")
    public abstract PlaylistItemResponse toPlaylistItemResponse(TrackObject trackObject);
}
