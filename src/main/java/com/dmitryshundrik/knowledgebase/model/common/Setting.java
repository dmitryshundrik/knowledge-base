package com.dmitryshundrik.knowledgebase.model.common;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "settings")
public class Setting {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String name;

    private String value;

}
