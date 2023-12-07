package com.dmitryshundrik.knowledgebase.model.gastronomy;

import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "cocktails")
public class Cocktail {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(unique = true)
    private String slug;

    private String title;

    @Column(columnDefinition = "text")
    private String about;

    @Column(columnDefinition = "text")
    private String ingredients;

    @Column(columnDefinition = "text")
    private String method;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<Image> imageList;

}
