package br.com.hubspot.integration.api.hubspot.controllers;

import br.com.hubspot.integration.api.hubspot.dtos.ContactRequestDTO;
import br.com.hubspot.integration.api.hubspot.services.contacts.IHubSpotContactsService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contacts")
public class HubSpotContactsController {

    private final IHubSpotContactsService service;

    public HubSpotContactsController(IHubSpotContactsService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createContact(@RequestBody ContactRequestDTO contact) {
        String response = service.createContact(contact);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/callback")
    public ResponseEntity<?> callback() {
        service.callbackContact("Successfully to returned by HubSpot Webhook");
        return ResponseEntity.ok().body("Callback  webhook completed");
    }

}
