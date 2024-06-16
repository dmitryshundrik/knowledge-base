package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "musicperiods")
@Data
@EqualsAndHashCode(callSuper = true)
public class MusicPeriod extends AbstractEntity {

    @Column(unique = true)
    private String slug;

    private String title;

    private String titleEn;

    private Integer approximateStart;

    private Integer approximateEnd;

    @Column(columnDefinition = "text")
    private String description;

}
