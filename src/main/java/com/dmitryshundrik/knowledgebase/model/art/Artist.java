package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.common.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "artist")
@Data
@EqualsAndHashCode(callSuper = true)
public class Artist extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "GENDER")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "IMAGE", columnDefinition = "text")
    private String image;

    @Column(name = "BORN")
    private Integer born;

    @Column(name = "DIED")
    private Integer died;

    @Column(name = "BIRTH_DATE")
    private LocalDate birthDate;

    @Column(name = "DEATH_DATE")
    private LocalDate deathDate;

    @Column(name = "APPROXIMATE_YEARS")
    private String approximateYears;

    @Column(name = "BIRTHPLACE")
    private String birthplace;

    @Column(name = "OCCUPATION")
    private String occupation;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    @JoinTable(
            name = "artist_events",
            joinColumns = {@JoinColumn(name = "artists_id")},
            inverseJoinColumns = {@JoinColumn(name = "events_id")}
    )
    private List<PersonEvent> events;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "artist")
    @OrderBy("created")
    private List<Painting> paintingList;
}
