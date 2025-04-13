package com.dmitryshundrik.knowledgebase.model.entity.spotify;

import lombok.Data;

@Data
public class CurrentSong {

    private String name;

    private String albumName;

    private String artists;

    private String artistSlug;

    private String musicGenres;

    private Integer year;

    private String yearEndRank;

    private String images;

    private boolean playing;
}
