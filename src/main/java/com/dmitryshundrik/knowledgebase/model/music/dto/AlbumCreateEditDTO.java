package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private String catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private List<UUID> collaboratorsUUID;

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

}
