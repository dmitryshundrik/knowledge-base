package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.enums.ResourceType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

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
