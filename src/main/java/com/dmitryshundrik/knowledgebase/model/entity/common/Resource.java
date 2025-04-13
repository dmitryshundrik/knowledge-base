package com.dmitryshundrik.knowledgebase.model.entity.common;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.util.enums.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
@Data
@EqualsAndHashCode(callSuper = true)
public class Resource extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @Column(name = "LINK", columnDefinition = "text")
    private String link;

    @Column(name = "RESOURCE_TYPE")
    @Enumerated(EnumType.STRING)
    private ResourceType resourceType;
}
