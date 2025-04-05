package br.com.hubspot.integration.api.external.hubspot;

import br.com.hubspot.integration.api.domain.oauth.IAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class HubspotAuthService implements IAuthService {

    @Value("${credentials.client-id}")
    private String clientId;

    @Value("${credentials.redirect-uri}")
    private String redirectUri;

    @Value("${credentials.scope}")
    private String scope;

    @Value("${credentials.client-secret}")
    private String clientSecret;


    @Value("${hubspot.url-token}")
    private String urlToken;



    @Override
    public String generateAuthorizationUrl() {
        return UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("app.hubspot.com")
                .path("/oauth/authorize")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("scope", scope.replace(" ", "%20"))
                .queryParam("response_type", "code")
                .build(true)
                .toUriString();
    }

    @Override
    public void exchangeAuthorizationCode(String code) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        params.add("client_secret", clientSecret);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(urlToken, request, String.class);
            System.out.println("Response: " + response.getBody());
        } catch (HttpClientErrorException e) {
            System.err.println("Erro ao trocar o código pelo token: " + e.getResponseBodyAsString());
            throw e;
        }
    }
}
