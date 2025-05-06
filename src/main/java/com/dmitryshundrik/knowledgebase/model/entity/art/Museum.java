package com.dmitryshundrik.knowledgebase.model.entity.art;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.model.entity.core.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "museum")
@Data
@EqualsAndHashCode(callSuper = true)
public class Museum extends AbstractEntity {

    @Column(name = "TITLE")
    private String title;

    @Column(name = "BASED")
    private String based;

    @Column(name = "FOUNDED")
    private Integer founded;

    @Column(name = "DESCRIPTION", columnDefinition = "text")
    private String description;

    @OneToMany
    private List<Image> imageList;
}
