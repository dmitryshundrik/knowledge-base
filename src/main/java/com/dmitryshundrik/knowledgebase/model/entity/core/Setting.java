package com.dmitryshundrik.knowledgebase.model.entity.core;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "setting")
@Data
@EqualsAndHashCode(callSuper = true)
public class Setting extends AbstractEntity {

    @Column(name = "NAME")
    private String name;

    @Column(name = "VALUE")
    private String value;
}
