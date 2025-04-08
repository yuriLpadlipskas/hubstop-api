package br.com.hubspot.integration.api.token;

import br.com.hubspot.integration.api.hubspot.dtos.TokenResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Slf4j
@Component
public class TokenCache {

    private TokenResponseDTO tokenResponseDTO;
    private Instant expiration;

    public void setToken(TokenResponseDTO tokenResponseDTO) {
        this.tokenResponseDTO = tokenResponseDTO;
        this.expiration = Instant.now().plusSeconds(tokenResponseDTO.expiresIn());
    }

    public TokenResponseDTO getToken() {
        return tokenResponseDTO == null ? null : tokenResponseDTO;
    }

    public boolean isExpired() {
        return tokenResponseDTO == null || Instant.now().isAfter(expiration);
    }

}
