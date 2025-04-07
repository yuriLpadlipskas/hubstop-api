package br.com.hubspot.integration.api.hubspot.exceptions;
import br.com.hubspot.integration.api.shared.HtmlResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HubSpotIntegrationException.class)
    public ResponseEntity<String> handleHubSpotError(HubSpotIntegrationException e) {
        return ResponseEntity.status(e.getStatusCode())
                .contentType(MediaType.TEXT_HTML)
                .body(HtmlResponses.AUTH_ERROR);
    }

}
