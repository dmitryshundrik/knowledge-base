package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "music_genre")
@Data
@EqualsAndHashCode(callSuper = true)
public class MusicGenre extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TITLE_EN")
    private String titleEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "MUSIC_GENRE_TYPE")
    private MusicGenreType musicGenreType;

    @Column(name = "COUNT")
    private Integer count;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
