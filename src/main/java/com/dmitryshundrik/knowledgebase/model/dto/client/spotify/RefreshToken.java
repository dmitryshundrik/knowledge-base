package com.dmitryshundrik.knowledgebase.model.dto.client.spotify;

import com.dmitryshundrik.knowledgebase.model.entity.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "refresh_token")
@Data
@EqualsAndHashCode(callSuper = true)
public class RefreshToken extends AbstractEntity {

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;
}
