package br.com.hubspot.integration.api.hubspot.client;

import br.com.hubspot.integration.api.hubspot.exceptions.HubSpotIntegrationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class HubSpotRestClient {

    private final RestTemplate restTemplate;

    public HubSpotRestClient() {
        this.restTemplate = new RestTemplate();
    }


    @Retryable(
            retryFor = {
                    HttpServerErrorException.class,
                    HttpClientErrorException.TooManyRequests.class
            },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1500, multiplier = 2)
    )
    public <T, R> R post(
            String url,
            T requestBody,
            Class<R> responseType,
            HttpHeaders headers,
            MediaType mediaType
    ) {
        log.info("Sending POST to {} with body: {}", url, requestBody);
        headers.setContentType(mediaType);
        HttpEntity<T> request = new HttpEntity<>(requestBody, headers);
        try {
            ResponseEntity<R> response = restTemplate.postForEntity(url, request, responseType);
            log.info("Response from POST {}: {}", url, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e ) {
            log.error("POST request failed: {}", e.getResponseBodyAsString());
            throw new HubSpotIntegrationException("Error to call HubSpot",e.getStatusCode().value(),e.getResponseBodyAsString());
        } catch (Exception e){
            log.error(" POST Error to call HubSpot: {}", e.getMessage());
            throw new HubSpotIntegrationException("Error to call HubSpot", 500, e.getMessage());
        }
    }

    public <T, R> R get(
            String url,
            Class<R> responseType
    ) {
        try {
            ResponseEntity<R> response = restTemplate.getForEntity(url, responseType);
            log.info("Response from GET {}: {}", url, response.getBody());
            return response.getBody();
        } catch (HttpClientErrorException | HttpServerErrorException e ) {
            log.error("GET request failed: {}", e.getResponseBodyAsString());
            throw new HubSpotIntegrationException("Error to call HubSpot",e.getStatusCode().value(),e.getResponseBodyAsString());
        } catch (Exception e){
            log.error("Error to call HubSpot: {}", e.getMessage(), e);
            throw new HubSpotIntegrationException("Error to call HubSpot", 500, e.getMessage());
        }
    }
}
