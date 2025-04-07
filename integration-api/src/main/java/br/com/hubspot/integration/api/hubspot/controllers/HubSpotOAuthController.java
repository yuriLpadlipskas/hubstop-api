package br.com.hubspot.integration.api.hubspot.controllers;

import br.com.hubspot.integration.api.hubspot.services.oauth.IHubSpotOAuthService;
import br.com.hubspot.integration.api.shared.HtmlResponses;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping("/oauth")
public class HubSpotOAuthController {

    private final IHubSpotOAuthService service;

    public HubSpotOAuthController(IHubSpotOAuthService service) {
        this.service = service;
    }

    @GetMapping("/authorize-url")
    public ResponseEntity<String> getAuthorizationUrl() {
        String url = service.generateAuthorizationUrl();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

    @GetMapping("/callback")
    public ResponseEntity<?> callback(@RequestParam String code) {
        service.exchageCodeToToken(code);
        return ResponseEntity.ok().contentType(MediaType.TEXT_HTML).body(HtmlResponses.AUTH_SUCCESS);
    }

}
