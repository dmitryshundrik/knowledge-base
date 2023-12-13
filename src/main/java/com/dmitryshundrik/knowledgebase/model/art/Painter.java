package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.common.PersonEvent;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "painters")
public class Painter {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(unique = true)
    private String slug;

    private String nickName;

    private String firstName;

    private String lastName;

    @Column(columnDefinition = "text")
    private String image;

    private Integer born;

    private Integer died;

    private LocalDate birthDate;

    private LocalDate deathDate;

    private String approximateYears;

    private String birthplace;

    private String occupation;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    @JoinTable(
            name = "painters_events",
            joinColumns = {@JoinColumn(name = "painters_id")},
            inverseJoinColumns = {@JoinColumn(name = "events_id")}
    )
    private List<PersonEvent> events;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "painter")
    @OrderBy("created")
    private List<Painting> paintingList;

}
