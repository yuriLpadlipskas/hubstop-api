package br.com.hubspot.integration.api.hubspot.controllers;

import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;
import br.com.hubspot.integration.api.hubspot.services.contacts.IHubSpotContactsService;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@RestController
@RequestMapping("/contacts")
public class HubSpotContactsController {

    private final IHubSpotContactsService service;

    public HubSpotContactsController(IHubSpotContactsService service) {
        this.service = service;
    }

    @Retryable(
            retryFor = { HttpServerErrorException.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1500)
    )
    @PostMapping("/create")
    public ResponseEntity<String> createContact(@RequestBody ContactRequestDTO contact) {
        String response = service.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<?> callback() {
        service.callbackContact("Testando callback de prod");
        return ResponseEntity.ok().body("Callback do webhook completado");
    }

}
