package com.dmitryshundrik.knowledgebase.entity.common;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "article")
@Data
@EqualsAndHashCode(callSuper = true)
public class Article extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;
}
