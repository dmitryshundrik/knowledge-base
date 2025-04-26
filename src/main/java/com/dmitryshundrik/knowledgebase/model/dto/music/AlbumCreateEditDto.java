package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.TITLE_MUST_NOT_BE_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlbumCreateEditDto {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
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

    private List<CompositionViewDto> compositions;
}
