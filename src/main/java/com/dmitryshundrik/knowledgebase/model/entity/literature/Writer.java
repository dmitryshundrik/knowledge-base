package com.dmitryshundrik.knowledgebase.model.entity.literature;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "writer")
@Data
@EqualsAndHashCode(callSuper = true)
public class Writer extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "NICK_NAME")
    private String nickName;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "GENDER")
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

    @Column(name = "BIRTHPLACE")
    private String birthplace;

    @Column(name = "OCCUPATION")
    private String occupation;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<PersonEvent> events;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Prose> proseList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Quote> quoteList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "writer")
    @OrderBy("created")
    private List<Word> wordList;

    @Column(name = "DATE_NOTIFICATION")
    private Boolean dateNotification;
}
