package com.dmitryshundrik.knowledgebase.model.dto.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.dmitryshundrik.knowledgebase.util.Constants.SLUG_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.TITLE_MUST_NOT_BE_BLANK;
import static com.dmitryshundrik.knowledgebase.util.Constants.YEAR_MUST_NOT_BE_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearInMusicCreateEditDTO {

    @NotBlank(message = SLUG_MUST_NOT_BE_BLANK)
    private String slug;

    @NotBlank(message = TITLE_MUST_NOT_BE_BLANK)
    private String title;

    @NotNull(message = YEAR_MUST_NOT_BE_NULL)
    private Integer year;

    private String bestMaleSingerId;

    private String bestFemaleSingerId;

    private String bestGroupId;

    private String bestComposerId;

    private String aotyListDescription;

    private String aotySpotifyLink;

    private String sotyListDescription;

    private String sotySpotifyLink;
}
