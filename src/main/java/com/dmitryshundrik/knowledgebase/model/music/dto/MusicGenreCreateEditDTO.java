package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.dmitryshundrik.knowledgebase.util.Constants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicGenreCreateEditDTO {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    private String title;

    private String titleEn;

    @NotNull(message = MUSIC_GENRE_MUST_NOT_BE_BLANK)
    private MusicGenreType musicGenreType;

    private Integer count;

    private String description;
}
