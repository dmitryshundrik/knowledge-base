package com.dmitryshundrik.knowledgebase.entity.spotify;

import com.dmitryshundrik.knowledgebase.entity.AbstractEntity;
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
