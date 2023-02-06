package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "lyrics")
public class Lyric {

    @Id
    @GeneratedValue
    private UUID id;

    private String language;

    private String text;

    private boolean isOriginal;

}
