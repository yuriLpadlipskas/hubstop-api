package br.com.hubspot.integration.api.hubspot.factory;

import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class HubSpotUrlFactory {

    public String buildAuthUrl(String clientId, String redirectUri, String scope){
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

}
