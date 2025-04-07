package br.com.hubspot.integration.api.hubspot.services.oauth;

import br.com.hubspot.integration.api.hubspot.dtos.TokenResponseDTO;

public interface IHubSpotOAuthService {
    String generateAuthorizationUrl();
    void exchageCodeToToken(String code);
    TokenResponseDTO refreshAccessToken(String refreshToken);
}
