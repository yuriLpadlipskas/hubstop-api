package br.com.hubspot.integration.api.hubspot.services.oauth.impl;

import br.com.hubspot.integration.api.auth.TokenCache;
import br.com.hubspot.integration.api.hubspot.client.HubSpotRestClient;
import br.com.hubspot.integration.api.hubspot.dtos.HubSpotProfileResponseDTO;
import br.com.hubspot.integration.api.hubspot.dtos.TokenResponseDTO;
import br.com.hubspot.integration.api.hubspot.factory.HubSpotUrlFactory;
import br.com.hubspot.integration.api.hubspot.services.oauth.IHubSpotOAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@Slf4j
public class HubSpotOAuthServiceImpl implements IHubSpotOAuthService {

    private final HubSpotRestClient hubspotRestClient;
    private final HubSpotUrlFactory hubSpotUrlFactory;
    private final TokenCache tokenCache;
    private final String clientId;
    private final String redirectUri;
    private final String scope;
    private final String clientSecret;
    private final String urlToken;
    private final String urlProfile;
    private final String urlRedirectHome;
    private final String suffixRedirectHome;

    public HubSpotOAuthServiceImpl(
            HubSpotRestClient hubspotRestClient, HubSpotUrlFactory hubSpotUrlFactory, TokenCache tokenCache,
            @Value("${hubspot.credentials.client.id}") String clientId,
            @Value("${hubspot.credentials.redirect.uri}") String redirectUri,
            @Value("${hubspot.credentials.scope}") String scope,
            @Value("${hubspot.credentials.client.secret}") String clientSecret,
            @Value("${hubspot.url.token}") String urlToken,
            @Value("${hubspot.url.profile}") String urlProfile,
            @Value("${hubspot.url.redirect.home.url}") String urlRedirectHome,
            @Value("${hubspot.url.redirect.home.suffix}")String suffixRedirectHome) {
        this.hubspotRestClient = hubspotRestClient;
        this.hubSpotUrlFactory = hubSpotUrlFactory;
        this.tokenCache = tokenCache;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.scope = scope;
        this.clientSecret = clientSecret;
        this.urlToken = urlToken;
        this.urlProfile = urlProfile;
        this.urlRedirectHome = urlRedirectHome;
        this.suffixRedirectHome = suffixRedirectHome;
    }

    @Override
    public String generateAuthorizationUrl() {
        return hubSpotUrlFactory.buildAuthUrl(clientId,redirectUri,scope);
    }

    @Override
    public String exchageCodeToToken(String code) {
        log.info("1.0 - init exchageCodeToToken code: {}", code);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        log.info("2.0 - exchageCodeToToken params: {}", params);
        TokenResponseDTO response = hubspotRestClient.post(urlToken,params,TokenResponseDTO.class,new HttpHeaders(),MediaType.APPLICATION_FORM_URLENCODED);
        log.info("3.0 - put token in cache: {}");
        tokenCache.setToken(response);
        log.info("4.0 - Token in cache: {}", tokenCache.getToken());;
        log.info("5.0 - start method Generate URL home hubspot: {}");
        String urlRedirect = generateRedirectHomeApp(tokenCache.getToken().accessToken());
        log.info("6.0 -  Generated URL home hubspot: {}",urlRedirect);
        return urlRedirect;
    }

    @Override
    public TokenResponseDTO refreshAccessToken(String refreshToken) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("refresh_token",  (refreshToken == null || refreshToken.isBlank()) ? "INVALID_TOKEN" : refreshToken);
        return hubspotRestClient.post(urlToken, params, TokenResponseDTO.class,new HttpHeaders(), MediaType.APPLICATION_FORM_URLENCODED);
    }

    @Override
    public String generateRedirectHomeApp(String token) {
        HubSpotProfileResponseDTO response = hubspotRestClient.get(urlProfile.concat(token), HubSpotProfileResponseDTO.class);
        return urlRedirectHome.concat(response.hubId().toString()).concat(suffixRedirectHome);
    }


}
