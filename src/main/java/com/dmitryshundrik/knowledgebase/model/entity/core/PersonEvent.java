package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person_event")
@Data
@EqualsAndHashCode(callSuper = true)
public class PersonEvent extends AbstractEntity {

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "ANOTHER_YEAR")
    private Integer anotherYear;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
