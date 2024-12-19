package com.dmitryshundrik.knowledgebase.dto.music;

import com.dmitryshundrik.knowledgebase.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.util.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicianManagementResponseDto {

    private Instant created;

    private String slug;

    private String nickName;

    private Gender gender;

    private Integer born;

    private Integer died;

    private Integer founded;

    private String birthplace;

    private String based;

    private String occupation;

    private String catalogTitle;

    private List<MusicPeriod> musicPeriods;

    private List<MusicGenre> musicGenres;

    private String image;
}
