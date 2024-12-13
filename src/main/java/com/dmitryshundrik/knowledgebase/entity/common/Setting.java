package com.dmitryshundrik.knowledgebase.entity.common;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
