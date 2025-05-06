package com.dmitryshundrik.knowledgebase.model.entity.gastronomy;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.entity.core.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "cocktail")
@Data
@EqualsAndHashCode(callSuper = true)
public class Cocktail extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ABOUT", columnDefinition = "text")
    private String about;

    @Column(name = "INGREDIENTS", columnDefinition = "text")
    private String ingredients;

    @Column(name = "METHOD", columnDefinition = "text")
    private String method;

    @OneToMany(cascade = CascadeType.ALL)
    @OrderBy("created")
    private List<Image> imageList;
}
