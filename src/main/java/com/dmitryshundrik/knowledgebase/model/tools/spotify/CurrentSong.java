package com.dmitryshundrik.knowledgebase.model.tools.spotify;

import lombok.Data;

@Data
public class CurrentSong {

    private String name;

    private String albumName;

    private String artists;

    private String artistSlug;

    private String musicGenres;

    private String images;

    private boolean playing;

}
