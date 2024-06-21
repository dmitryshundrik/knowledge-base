package com.dmitryshundrik.knowledgebase.model.gastronomy;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
public class Recipe extends AbstractEntity {

    @Column(unique = true)
    private String slug;

    private String title;

    @Enumerated(EnumType.STRING)
    private Country country;

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
