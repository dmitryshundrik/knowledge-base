package com.dmitryshundrik.knowledgebase.model.entity.art;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.entity.core.PersonEvent;
import com.dmitryshundrik.knowledgebase.model.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
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

    @Column(name = "DATE_NOTIFICATION")
    private Boolean dateNotification;
}
