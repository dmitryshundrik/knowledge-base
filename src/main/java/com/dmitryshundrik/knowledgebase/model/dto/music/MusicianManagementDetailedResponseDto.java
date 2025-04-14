package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import com.dmitryshundrik.knowledgebase.model.entity.music.MusicPeriod;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import com.dmitryshundrik.knowledgebase.model.enums.SortType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicianManagementDetailedResponseDto {

    private String created;

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

    private SortType albumsSortType;

    private SortType compositionsSortType;

    private String image;

    private Boolean dateNotification;
}
