package com.dmitryshundrik.knowledgebase.model.music;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "sotylistcompositions")
public class SOTYListComposition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long rank;

    @OneToOne(cascade = CascadeType.ALL)
    private Composition composition;
}
