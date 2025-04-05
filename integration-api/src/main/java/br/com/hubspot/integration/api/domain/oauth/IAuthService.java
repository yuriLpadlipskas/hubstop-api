package br.com.hubspot.integration.api.domain.oauth;

public interface IAuthService {
    String generateAuthorizationUrl();
    void exchangeAuthorizationCode(String code);
}
