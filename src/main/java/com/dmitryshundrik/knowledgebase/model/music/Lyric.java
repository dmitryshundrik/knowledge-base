package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "lyrics")
public class Lyric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String language;

    private String text;

    private boolean isOriginal;

}