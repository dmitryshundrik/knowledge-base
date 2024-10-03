package com.dmitryshundrik.knowledgebase.model.music.dto;

import com.dmitryshundrik.knowledgebase.model.music.MusicGenre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MusicianAllDto {

    private String slug;

    private String nickName;

    private Integer born;

    private Integer founded;

    private List<MusicGenre> musicGenres;

    public MusicianAllDto(String slug, String nickName, Integer born, Integer founded) {
        this.slug = slug;
        this.nickName = nickName;
        this.born = born;
        this.founded = founded;
    }
}
