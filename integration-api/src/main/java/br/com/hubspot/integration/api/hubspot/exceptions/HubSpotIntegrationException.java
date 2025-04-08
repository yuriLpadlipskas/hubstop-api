package br.com.hubspot.integration.api.hubspot.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class HubSpotIntegrationException extends RuntimeException {

    private final int statusCode;

    private final String response;

    public HubSpotIntegrationException(String message, int statusCode, String response) {
        super(message);
        this.statusCode = statusCode;
        this.response = response;
    }

}
