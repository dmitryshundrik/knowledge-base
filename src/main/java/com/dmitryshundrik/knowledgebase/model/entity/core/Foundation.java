package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "foundation")
@Data
@EqualsAndHashCode(callSuper = true)
public class Foundation extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "LINK", columnDefinition = "text")
    private String link;
}
