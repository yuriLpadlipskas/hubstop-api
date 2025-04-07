package br.com.hubspot.integration.api.config;

import br.com.hubspot.integration.api.hubspot.dtos.TokenResponseDTO;
import br.com.hubspot.integration.api.hubspot.services.oauth.IHubSpotOAuthService;
import org.springframework.stereotype.Component;

@Component
public class TokenManager {
    private final TokenCache cache;
    private final IHubSpotOAuthService hubspotOAuthService;

    public TokenManager(TokenCache cache, IHubSpotOAuthService hubspotOAuthService) {
        this.cache = cache;
        this.hubspotOAuthService = hubspotOAuthService;
    }

    public String getValidToken() {
        if (cache.isExpired()) {
            cache.clearCache();
            TokenResponseDTO newToken = hubspotOAuthService.refreshAccessToken(cache.getToken().refreshToken());
            cache.setToken(newToken);
        }
        return cache.getToken().accessToken();
    }

    public String generateBearerToken() {
        return "Bearer " + getValidToken();
    }

}
