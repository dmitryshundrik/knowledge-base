package com.dmitryshundrik.knowledgebase.model.entity.literature;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "quote")
@Data
@EqualsAndHashCode(callSuper = true)
public class Quote extends AbstractEntity {

    @ManyToOne
    private Writer writer;

    @ManyToOne
    private Prose prose;

    @Column(name = "PUBLICATION")
    private String publication;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "PAGE")
    private Integer page;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "DESCRIPTION_HTML", columnDefinition = "text")
    private String descriptionHtml;
}
