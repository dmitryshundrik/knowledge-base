package com.dmitryshundrik.knowledgebase.model.art;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "galleries")
public class Gallery {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    private String title;

    private String based;

    private Integer founded;

    @Column(columnDefinition = "text")
    private String description;

    @OneToMany
    private List<Image> imageList;

}
