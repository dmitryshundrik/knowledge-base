package com.dmitryshundrik.knowledgebase.model.gastronomy;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.common.Image;
import com.dmitryshundrik.knowledgebase.model.common.enums.Country;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "recipe")
@Data
@EqualsAndHashCode(callSuper = true)
public class Recipe extends AbstractEntity {

    @Column(name = "SLUG", unique = true)
    private String slug;

    @Column(name = "TITLE")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "COUNTRY")
    private Country country;

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
