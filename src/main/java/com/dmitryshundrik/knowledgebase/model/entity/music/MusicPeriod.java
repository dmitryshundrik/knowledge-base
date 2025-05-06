package com.dmitryshundrik.knowledgebase.model.entity.music;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

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
