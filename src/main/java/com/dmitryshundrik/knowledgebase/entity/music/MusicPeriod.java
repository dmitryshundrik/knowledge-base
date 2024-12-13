package com.dmitryshundrik.knowledgebase.entity.music;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "music_period")
@Data
@EqualsAndHashCode(callSuper = true)
public class MusicPeriod extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "TITLE_EN")
    private String titleEn;

    @Column(name = "APPROXIMATE_START")
    private Integer approximateStart;

    @Column(name = "APPROXIMATE_END")
    private Integer approximateEnd;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
