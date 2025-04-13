package com.dmitryshundrik.knowledgebase.model.dto.music;

import com.dmitryshundrik.knowledgebase.model.entity.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicianAllPageResponseDto {

    private String slug;

    private String nickName;

    private Integer born;

    private Integer founded;

    private List<MusicGenre> musicGenres;

    public MusicianAllPageResponseDto(String slug, String nickName, Integer born, Integer founded) {
        this.slug = slug;
        this.nickName = nickName;
        this.born = born;
        this.founded = founded;
    }
}
