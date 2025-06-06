package com.dmitryshundrik.knowledgebase.repository.tools;

import com.dmitryshundrik.knowledgebase.model.dto.client.spotify.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {

}
