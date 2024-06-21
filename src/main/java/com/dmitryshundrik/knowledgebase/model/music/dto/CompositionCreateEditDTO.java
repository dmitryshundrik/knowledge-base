package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompositionCreateEditDTO {

    @NotBlank(message = "slug may not be blank")
    private String slug;

    @NotBlank(message = "title may not be blank")
    private String title;

    private Integer catalogNumber;

    private String musicianNickname;

    private String musicianSlug;

    private String albumId;

    private String feature;

    private Integer year;

    private List<MusicGenre> classicalGenres;

    private List<MusicGenre> contemporaryGenres;

    private Double rating;

    private Integer yearEndRank;

    private Integer essentialCompositionsRank;

    private String highlights;

    private String description;

    private String lyrics;

    private String translation;

}
