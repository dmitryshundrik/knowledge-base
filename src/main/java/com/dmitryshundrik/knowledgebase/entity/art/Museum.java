package com.dmitryshundrik.knowledgebase.entity.art;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
import com.dmitryshundrik.knowledgebase.entity.common.Image;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
