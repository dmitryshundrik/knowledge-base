package com.dmitryshundrik.knowledgebase.model.gastronomy;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "cocktails")
@Data
public class Cocktail extends AbstractEntity {

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
