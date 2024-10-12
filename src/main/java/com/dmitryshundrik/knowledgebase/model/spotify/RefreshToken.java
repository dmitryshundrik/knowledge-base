package com.dmitryshundrik.knowledgebase.model.spotify;

import com.dmitryshundrik.knowledgebase.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "refresh_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends AbstractEntity {

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
}
