package br.com.hubspot.integration.api.hubspot.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HubSpotIntegrationException.class)
    public ResponseEntity<Map<String, Object>> handleHubSpotIntegrationException(HubSpotIntegrationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("statusCode", ex.getStatusCode());
        body.put("response", ex.getResponse());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }
}
