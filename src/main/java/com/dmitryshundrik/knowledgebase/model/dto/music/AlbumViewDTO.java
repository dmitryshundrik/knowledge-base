package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class AlbumViewDTO {

    private String created;

    private String slug;

    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private List<MusicianSelectDTO> collaborators;

    private String feature;

    private String artwork;

    private Integer year;

    private List<MusicGenre> musicGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialAlbumsRank;

    private String highlights;

    private String description;

    private List<CompositionViewDTO> compositions;
}
