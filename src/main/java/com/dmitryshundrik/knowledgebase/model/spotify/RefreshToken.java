package com.dmitryshundrik.knowledgebase.model.spotify;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "refresh_token")
public class RefreshToken {

    @Id
    @GeneratedValue
    private UUID id;

    private Instant created;

    @Column(name = "refresh_token")
    private String refreshToken;


}
