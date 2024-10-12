package com.dmitryshundrik.knowledgebase.model.music;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "year_in_music")
@Data
@EqualsAndHashCode(callSuper = true)
public class YearInMusic extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "YEAR")
    private Integer year;

    @ManyToOne
    private Musician bestMaleSinger;

    @ManyToOne
    private Musician bestFemaleSinger;

    @ManyToOne
    private Musician bestGroup;

    @ManyToOne
    private Musician bestComposer;

    @Column(name = "AOTY_LIST_DESCRIPTION", columnDefinition = "text")
    private String aotyListDescription;

    @Column(name = "AOTY_SPOTIFY_LINK", columnDefinition = "text")
    private String aotySpotifyLink;

    @Column(name = "SOTY_LIST_DESCRIPTION", columnDefinition = "text")
    private String sotyListDescription;

    @Column(name = "SOTY_SPOTIFY_LINK", columnDefinition = "text")
    private String sotySpotifyLink;
}
