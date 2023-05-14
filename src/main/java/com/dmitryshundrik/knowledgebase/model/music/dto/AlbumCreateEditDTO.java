package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class AlbumCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String feature;

    private String artwork;

    private Integer year;

    private List<MusicGenre> classicalGenres;

    private List<MusicGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialAlbumsRank;

    private String highlights;

    private String description;

    private List<CompositionViewDTO> compositions;

    public AlbumCreateEditDTO() {

    }
}
