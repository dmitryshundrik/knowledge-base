package com.dmitryshundrik.knowledgebase.model.entity.music;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.enums.MusicGenreType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

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
