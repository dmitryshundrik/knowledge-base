package com.dmitryshundrik.knowledgebase.model.common;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.*;

@Entity
@Table(name = "image")
@Data
@EqualsAndHashCode(callSuper = true)
public class Image extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SLUG")
    private String slug;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "DATA")
    private String data;
}
