package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.music.enums.MusicGenreType;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "musicgenres")
public class MusicGenre {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String slug;

    private String title;

    private String titleEn;

    private MusicGenreType musicGenreType;

    private Integer count;

    @Column(columnDefinition = "text")
    private String description;

}
